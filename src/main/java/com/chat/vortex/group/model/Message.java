package com.chat.vortex.group.model;

import java.util.Optional;
import java.util.UUID;
import java.time.Instant;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
   @Id private UUID messageId;
   private UUID userId;
   private Optional<UUID> channelId;
   private Instant createdAt;
   private String content;
}
