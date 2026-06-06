package com.chat.vortex.group.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("group")
public class GroupServer {
  @Id
  private String groupId;

}
