package com.chat.vortex.group.services;

import java.util.UUID;
import java.lang.String;
import com.chat.vortex.group.model.GroupServer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GroupService {
  Mono<GroupServer> createGroup(UUID ownerId, String groupName);

  Mono<GroupServer> getServerById(UUID groupId);

  Mono<Void> removeGroup(UUID groupId);

  Flux<GroupServer> getAllServers();
}
