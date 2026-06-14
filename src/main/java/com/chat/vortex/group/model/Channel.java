package com.chat.vortex.group.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("channel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Channel {
  @Id
  private UUID channelId;
  private UUID groupId;
  private String channelName;
  private ChannelType type;
  private int userLimit;
}
