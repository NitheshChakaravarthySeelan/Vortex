package com.chat.vortex.group.services;

import java.util.UUID;

import com.chat.vortex.group.model.Message;

import reactor.core.publisher.Mono;

public interface ChatService {
    
    public Mono<Message> createChat( UUID userId, UUID channelId,  String content);

    public Mono<Void> deleteChat(UUID chatId, UUID userId);
}