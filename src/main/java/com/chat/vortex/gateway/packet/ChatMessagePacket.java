package com.chat.vortex.gateway.packet;

import java.util.UUID;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a packet sent by a client containing a chat message.
 * This is a Data Transfer Object (DTO) used to receive message content and target channel
 * over the WebSocket gateway.
 */
@Data
public class ChatMessagePacket {
    /**
     * The unique identifier of the channel where the message should be sent.
     */
    @JsonProperty("channelId")
    private UUID channelId;
    
    /**
     * The actual text content of the message sent by the user.
     */
    @JsonProperty("content")
    private String content;
}