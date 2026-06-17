package com.chat.vortex.chat.model;

import java.util.Optional;
import java.util.UUID;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a chat message entity in the database.
 * This class serves as the primary domain model for messages sent within channels.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("messages")
public class Message {
   /**
    * The unique identifier for this message.
    * Mapped as the primary key in the underlying data store.
    */
   @Id private UUID messageId;
   
   /**
    * The unique identifier of the user who authored this message.
    */
   private UUID userId;
   
   /**
    * The unique identifier of the channel where this message belongs.
    */
   private UUID channelId;
   
   /**
    * The timestamp indicating when this message was originally created.
    */
   private Instant createdAt;
   
   /**
    * The textual content of the message.
    */
   private String content;
}
