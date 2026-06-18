package com.chat.vortex.kafka.event;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an event indicating that a new chat message has been created.
 * This event is published to a message broker (like Kafka) to distribute the message
 * to other consumers, such as push notification services, archiving services, or 
 * other instances of the WebSocket gateway for real-time delivery.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCreatedEvent extends Event{
    /**
     * The unique identifier of the newly created message.
     */
    private UUID messageId;
    
    /**
     * The unique identifier of the user who sent the message.
     */
    private UUID userId;
    
    /**
     * The unique identifier of the channel where the message was posted.
     */
    private UUID channelId;
    
    /**
     * The actual text content of the message.
     */
    private String content;
    
    /**
     * The exact timestamp (epoch millis) when the message was successfully created.
     */
    private Long createdAt;
}