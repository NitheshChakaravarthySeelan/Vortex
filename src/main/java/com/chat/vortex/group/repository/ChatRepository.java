package com.chat.vortex.group.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.chat.vortex.group.model.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatRepository extends ReactiveCrudRepository<Message, UUID> {
    Mono<Message> findById(UUID chatId);

    Flux<Message> findByUserId(UUID userId);

    Mono<Message> findByIdAndUserId(UUID chatId, UUID userId);
}