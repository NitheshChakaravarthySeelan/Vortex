package com.chat.vortex.gateway.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.chat.vortex.gateway.packet.AuthenticatePacket;
import com.chat.vortex.gateway.session.SessionManager;
import com.chat.vortex.shared.model.OperationType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import reactor.core.publisher.Mono;

@Component
public class AuthenticateHandler implements PacketHandler {

    private final ObjectMapper mapper = new ObjectMapper();
    private final SessionManager sessionManager;

    public AuthenticateHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean supports(OperationType type) {
        return type == OperationType.AUTHENTICATE;
    }

    @Override
    public void handle(WebSocketSession session, JsonNode data) {
        AuthenticatePacket packet = mapper.convertValue(data, AuthenticatePacket.class);
        sessionManager.connect(packet.getUserId(), session);
        String json = toJson(Map.of("operation", "AUTHENTICATED", "data", Map.of("status", "authenticated", "userId", packet.getUserId())));
        session.send(Mono.just(session.textMessage(json))).subscribe();
    }

    private String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            return "{}";
        }
    }
}
