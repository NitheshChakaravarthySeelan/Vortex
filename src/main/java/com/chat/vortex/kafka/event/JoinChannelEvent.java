package com.chat.vortex.kafka.event;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an event indicating that a user has successfully joined a channel.
 * This event is typically published to a message broker (like Kafka) to notify other
 * services or broadcast the state change across distributed gateway instances.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinChannelEvent extends Event{
    /**
     * The unique identifier of the user who joined the channel.
     */
    private UUID userId;
    
    /**
     * The unique identifier of the channel that was joined.
     */
    private UUID channelId;
    
    /**
     * The exact timestamp when this join event occurred.
     */
    private Instant createdAt;
}
