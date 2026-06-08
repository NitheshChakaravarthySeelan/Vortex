package com.chat.vortex.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @Column("user_id")
  private UUID userId;
  @Column("user_name")
  private String userName;
  private String email;
  @Column("hashed_password")
  private String hashedPassword;
  @Column("created_at")
  private Instant createdAt;
}
