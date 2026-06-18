package com.chat.vortex.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.time.Instant;
import java.util.Set;

import com.chat.vortex.chat.model.Message;
import com.chat.vortex.chat.repository.MessageRepository;
import com.chat.vortex.kafka.event.Event;
import com.chat.vortex.kafka.event.MessageCreatedEvent;
import com.chat.vortex.gateway.session.SessionRegistry;
import com.chat.vortex.shared.model.OperationType;
import com.chat.vortex.shared.model.Packet;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

@Service
public class MessageConsumer implements Consumer{
    private final MessageRepository messageRepository;
    private final SessionRegistry sessionRegistry;
    private final ObjectMapper objectMapper;

    public MessageConsumer(MessageRepository messageRepository, SessionRegistry sessionRegistry, ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.sessionRegistry = sessionRegistry;
        this.objectMapper = objectMapper;
    }

    @Override
    @KafkaListener(
        topics = "messages",
        groupId = "gateway"
    )
    public void consume(Event event) {
        if (event instanceof MessageCreatedEvent messageCreatedEvent) {
            // Save to the db
            Message message = new Message(
                messageCreatedEvent.getMessageId(),
                messageCreatedEvent.getUserId(),
                messageCreatedEvent.getChannelId(),
                Instant.ofEpochMilli(messageCreatedEvent.getCreatedAt()),
                messageCreatedEvent.getContent()
            );
            messageRepository.save(message);

            // broadcast to all the users in the channel
            try {
                Packet packet = new Packet(OperationType.CHAT_MESSAGE, objectMapper.valueToTree(message));
                String jsonResponse = objectMapper.writeValueAsString(packet);

                Set<WebSocketSession> sessions = sessionRegistry.getChannelUsers(message.getChannelId());
                for (WebSocketSession session : sessions) {
                    session.send(Mono.just(session.textMessage(jsonResponse))).subscribe();
                }
            } catch (Exception e) {
                System.err.println("Failed to broadcast message: " + e.getMessage());
            }
        }
    }
}
