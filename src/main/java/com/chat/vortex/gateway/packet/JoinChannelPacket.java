package com.chat.vortex.gateway.packet;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a packet sent by a client to join a specific chat channel.
 * This is a Data Transfer Object (DTO) used specifically within the WebSocket gateway layer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinChannelPacket {
    /**
     * The unique identifier of the channel the user wishes to join.
     */
    private UUID channelId;
}
