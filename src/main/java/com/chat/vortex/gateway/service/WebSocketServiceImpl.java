package com.chat.vortex.gateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.chat.vortex.gateway.dispatcher.PacketDispatcher;
import com.chat.vortex.shared.model.Packet;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServiceImpl.class);
    private final ObjectMapper objectMapper;
    private final PacketDispatcher packetDispatcher;

    public WebSocketServiceImpl(ObjectMapper objectMapper, PacketDispatcher packetDispatcher) {
        this.objectMapper = objectMapper;
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.receive()
            .map(msg -> msg.getPayloadAsText())
            .map(payload -> {
                try {
                    return objectMapper.readValue(payload, Packet.class);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to parse packet", e);
                }
            })
            .doOnNext(packet -> {
                try {
                    packetDispatcher.dispatch(packet, session);
                } catch (Exception e) {
                    log.error("Error handling packet {}: {}", packet.operation(), e.getMessage(), e);
                }
            })
            .doOnError(e -> log.error("WebSocket error: {}", e.getMessage(), e))
            .then();
    }
}
