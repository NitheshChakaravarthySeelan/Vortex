package com.chat.vortex.gateway.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import com.chat.vortex.gateway.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/*
client -> websocket gateway -> kafka -> group chat service -> db
 */
@Component
@RequiredArgsConstructor
public class MyWebSocketHandler implements WebSocketHandler {

    private final WebSocketService webSocketService;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return webSocketService.handle(session);
    }
}
