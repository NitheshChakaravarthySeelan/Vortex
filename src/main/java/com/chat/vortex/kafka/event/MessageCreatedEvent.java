package com.chat.vortex.kafka.event;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class MessageCreatedEvent {
    private UUID messageId;
    private UUID userId;
    private UUID channelId;
    private String content;
    private Instant createdAt;
}