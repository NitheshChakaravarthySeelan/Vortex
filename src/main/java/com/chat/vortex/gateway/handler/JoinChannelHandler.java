package com.chat.vortex.gateway.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.chat.vortex.gateway.packet.JoinChannelPacket;
import com.chat.vortex.shared.model.OperationType;
import com.chat.vortex.gateway.session.SessionRegistry;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handles incoming WebSocket packets for joining a chat channel.
 * Implements the {@link PacketHandler} interface to specifically process
 * operations of type {@link OperationType#JOIN_CHANNEL}.
 */
@Component
public class JoinChannelHandler implements PacketHandler {
    private final ObjectMapper mapper = new ObjectMapper();
    private final SessionRegistry sessionRegistry;

    public JoinChannelHandler(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }
    
    /**
     * Determines whether this handler can process the given operation type.
     *
     * @param type The type of operation requested by the client.
     * @return true if the operation is JOIN_CHANNEL, false otherwise.
     */
    @Override
    public boolean supports(OperationType type) {
        return type == OperationType.JOIN_CHANNEL;
    }

    /**
     * Processes the join channel packet sent by the user over WebSocket.
     * This method is responsible for parsing the JSON data into a strongly-typed packet
     * and subsequently handling the subscription logic.
     *
     * @param session The active WebSocket session of the user sending the packet.
     * @param data The raw JSON payload containing the packet details.
     */
    @Override
    public void handle(WebSocketSession session, JsonNode data) {
        // Convert JsonNode to JoinChannelPacket
        JoinChannelPacket packet = mapper.convertValue(data, JoinChannelPacket.class);
        sessionRegistry.addToChannel(packet.getChannelId(), session);
    }
    
}
