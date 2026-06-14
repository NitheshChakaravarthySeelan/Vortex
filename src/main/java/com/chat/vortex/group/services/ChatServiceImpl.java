package com.chat.vortex.group.services;

import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.chat.vortex.group.model.Message;
import com.chat.vortex.group.repository.ChatRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public Mono<Message> createChat(UUID userId, UUID channelId, String content) {
        Message newChat = new Message(UUID.randomUUID(), userId, channelId, Instant.now(), content);

        return chatRepository.save(newChat);
    }

    @Override
    public Mono<Void> deleteChat(UUID chatId, UUID userId) {
        return chatRepository.findByIdAndUserId(chatId, userId)
            .switchIfEmpty(Mono.error(new RuntimeException("chat is not there")))
            .flatMap(chatRepository::delete);
    }
}
