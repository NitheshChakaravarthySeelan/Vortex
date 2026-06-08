package com.chat.vortex.group.services;

import java.util.UUID;

import com.chat.vortex.group.model.Channel;
import com.chat.vortex.group.model.ChannelType;

import reactor.core.publisher.Mono;

public interface ChannelService {
  Mono<Channel> createChannel(UUID channelId, String channelName, UUID groupId, ChannelType type, int userLimit);

  Mono<Void> removeChannel(UUID channelId, UUID groupId);
}
