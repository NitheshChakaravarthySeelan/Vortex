package com.chat.vortex.chat.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.chat.vortex.chat.model.Message;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageRepository extends ReactiveCrudRepository<Message, UUID> {
    Mono<Message> findByMessageId(UUID messageId);

    Flux<Message> findByUserId(UUID userId);

    Mono<Message> findByMessageIdAndUserId(UUID messageId, UUID userId);
}