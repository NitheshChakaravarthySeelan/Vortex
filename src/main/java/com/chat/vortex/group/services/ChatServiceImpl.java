package com.chat.vortex.group.services;

import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.chat.vortex.chat.model.Message;
import com.chat.vortex.chat.repository.MessageRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ChatService.
 * Handles the business logic for creating and deleting messages from the underlying repository.
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final MessageRepository messageRepository;

    /**
     * Creates a new chat message and persists it to the database.
     * 
     * @param userId The ID of the author.
     * @param channelId The ID of the target channel.
     * @param content The text content of the message.
     * @return A Mono emitting the newly saved Message.
     */
    @Override
    public Mono<Message> createChat(UUID userId, UUID channelId, String content) {
        Message newChat = new Message(UUID.randomUUID(), userId, channelId, Instant.now(), content);

        return messageRepository.save(newChat);
    }

    /**
     * Deletes a chat message if the requesting user is the author.
     * 
     * @param chatId The ID of the message to delete.
     * @param userId The ID of the user requesting deletion.
     * @return A Mono that completes when the deletion is successful.
     */
    @Override
    public Mono<Void> deleteChat(UUID chatId, UUID userId) {
        return messageRepository.findByMessageIdAndUserId(chatId, userId)
            .switchIfEmpty(Mono.error(new RuntimeException("chat is not there")))
            .flatMap(messageRepository::delete);
    }
}
