package com.chat.vortex.gateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Service
public interface WebSocketService {
    public Mono<Void> handle(WebSocketSession session);    
}
