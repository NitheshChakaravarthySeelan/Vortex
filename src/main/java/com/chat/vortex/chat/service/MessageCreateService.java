package com.chat.vortex.chat.service;

import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import com.chat.vortex.gateway.packet.ChatMessagePacket;
import com.chat.vortex.gateway.session.SessionRegistry;
import com.chat.vortex.kafka.event.MessageCreatedEvent;
import com.chat.vortex.kafka.producer.MessageProducer;

/**
 * Service responsible for creating new messages sent by users.
 * Validates the user session and dispatches the event to the Kafka broker.
 */
@Service
public class MessageCreateService {
   private final SessionRegistry sessionRegistry;
   private final MessageProducer messageProducer;

   /**
    * Constructs a new MessageCreateService.
    * 
    * @param sessionRegistry To verify and fetch the user UUID associated with a session.
    * @param messageProducer To publish the message event to Kafka.
    */
   public MessageCreateService(SessionRegistry sessionRegistry, MessageProducer messageProducer) {
       this.sessionRegistry = sessionRegistry;
       this.messageProducer = messageProducer;
   }

   /**
    * Processes a raw chat message packet, enriches it with user information,
    * and publishes it for async processing and broadcast.
    * 
    * @param chatMessage The DTO containing the content and target channel.
    * @param session The sender's WebSocket session.
    * @throws IllegalStateException if the session is not authenticated.
    */
   public void sendMessage(ChatMessagePacket chatMessage, WebSocketSession session) {
      UUID userId = sessionRegistry.getUserSession(session.getId()).orElse(null);
      if (userId == null) {
          throw new IllegalStateException("User not authenticated for session: " + session.getId());
      }

      MessageCreatedEvent messageCreatedEvent = new MessageCreatedEvent(
         UUID.randomUUID(), // messageId
         userId,
         chatMessage.getChannelId(),
         chatMessage.getContent(),
         Instant.now()
      );

      try {
         messageProducer.publish(messageCreatedEvent);
      } catch (Exception e) {
         throw new RuntimeException("Failed to publish message", e);
      }
   }

}
