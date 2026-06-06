package com.chat.vortex.shared.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("member")
public class Member {
  @Id
  @Column("user_id")
  private String userId;
}
