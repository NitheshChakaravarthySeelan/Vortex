package com.chat.vortex.gateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.chat.vortex.gateway.dispatcher.PacketDispatcher;
import com.chat.vortex.shared.model.Packet;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

/**
 * Implementation of the WebSocketService that processes incoming WebSocket connections.
 * It is responsible for parsing raw text messages into standard Packets and forwarding
 * them to the PacketDispatcher.
 */
@Service
public class WebSocketServiceImpl implements WebSocketService{
   
    private final ObjectMapper objectMapper;
    private final PacketDispatcher packetDispatcher;

    /**
     * Constructs a WebSocketServiceImpl with the necessary dependencies.
     * 
     * @param objectMapper The Jackson object mapper for JSON deserialization.
     * @param packetDispatcher The dispatcher to route packets to the appropriate handler.
     */
    public WebSocketServiceImpl(ObjectMapper objectMapper, PacketDispatcher packetDispatcher) {
        this.objectMapper = objectMapper;
        this.packetDispatcher = packetDispatcher;
    }
    
    /**
     * Handles the active WebSocket session by continuously receiving messages,
     * converting them from JSON to Packet objects, and dispatching them.
     * 
     * @param session The active user WebSocket session.
     * @return A Mono that completes when the session is closed.
     */
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
            .doOnNext(packet -> packetDispatcher.dispatch(packet, session))
            .then();
    }
}
