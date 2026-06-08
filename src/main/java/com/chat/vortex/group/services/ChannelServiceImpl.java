package com.chat.vortex.group.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chat.vortex.group.model.Channel;
import com.chat.vortex.group.model.ChannelType;
import com.chat.vortex.group.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
  private final ChannelRepository channelRepository;

  @Override
  public Mono<Channel> createChannel(UUID channelId, String channelName, UUID groupId, ChannelType type, int limit) {
    return channelRepository.save(new Channel(
        channelId, groupId, channelName, type, limit));
  }

  @Override
  public Mono<Void> removeChannel(UUID groupId, UUID channelId) {
    return channelRepository
        .findByGroupIdAndChannelId(groupId, channelId)
        .switchIfEmpty(Mono.error(new RuntimeException("channel is not there")))
        .flatMap(channelRepository::delete);
  }
}
