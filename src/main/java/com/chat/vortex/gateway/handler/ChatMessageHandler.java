package com.chat.vortex.gateway.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.chat.vortex.gateway.packet.ChatMessagePacket;
import com.chat.vortex.shared.model.OperationType;
import com.chat.vortex.chat.service.MessageCreateService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handles incoming WebSocket packets for sending chat messages.
 * Implements the {@link PacketHandler} interface to specifically process
 * operations of type {@link OperationType#CHAT_MESSAGE}.
 */
@Component
public class ChatMessageHandler implements PacketHandler {
    private final ObjectMapper mapper = new ObjectMapper();
    private final MessageCreateService messageCreateService;

    public ChatMessageHandler(MessageCreateService messageCreateService) {
        this.messageCreateService = messageCreateService;
    }
    
    /**
     * Determines whether this handler can process the given operation type.
     *
     * @param type The type of operation requested by the client.
     * @return true if the operation is CHAT_MESSAGE, false otherwise.
     */
    @Override
    public boolean supports(OperationType type) {
        if (type == OperationType.CHAT_MESSAGE) {
            return true;
        }
        return false;
    }

    /**
     * Processes the chat message packet sent by the user over WebSocket.
     * This method parses the JSON data into a strongly-typed packet representing
     * the user's message and the destination channel.
     *
     * @param session The active WebSocket session of the user sending the message.
     * @param data The raw JSON payload containing the packet details (e.g., content, channelId).
     */
    @Override
    public void handle(WebSocketSession session, JsonNode data) {
        ChatMessagePacket chatPacket = mapper.convertValue(data, ChatMessagePacket.class);
        messageCreateService.sendMessage(chatPacket, session);
    }
}
