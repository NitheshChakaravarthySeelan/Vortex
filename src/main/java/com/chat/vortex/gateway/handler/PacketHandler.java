package com.chat.vortex.gateway.handler;

import org.springframework.web.reactive.socket.WebSocketSession;

import com.chat.vortex.shared.model.OperationType;
import com.fasterxml.jackson.databind.JsonNode;

public interface PacketHandler {
    boolean supports(OperationType type);

    void handle(WebSocketSession session, JsonNode data);
}
