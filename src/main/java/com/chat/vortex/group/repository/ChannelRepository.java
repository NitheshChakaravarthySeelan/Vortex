package com.chat.vortex.group.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.chat.vortex.group.model.Channel;
import com.chat.vortex.group.model.ChannelType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChannelRepository extends ReactiveCrudRepository<Channel, UUID> {
  Flux<Channel> findByGroupId(UUID groupId);

  Flux<Channel> findByGroupIdAndType(UUID groupId, ChannelType type);

  Mono<Channel> findByGroupIdAndChannelId(UUID groupId, UUID channelId);
}
