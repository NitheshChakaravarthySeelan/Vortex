package com.chat.vortex.group.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chat.vortex.group.model.GroupServer;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
  private final GroupRepository groupRepository;

  @Override
  public Mono<GroupServer> createGroup(String groupName, UUID ownerId) {
    GroupServer newGroup = new GroupServer(UUID.randomUUID(), groupName, ownerId);
    return groupRepository.save(newGroup);
  }

  @Override
  public Mono<GroupServer> getServerById(UUID groupId) {
    return groupRepository.findById(groupId)
        .switchIfEmpty(Mono.error(new RuntimeException("the group is not there")));
  }

  @Override
  public Flux<GroupServer> getAllServer() {
    return groupRepository.findAll();
  }

  @Override
  public Mono<Void> removeGroup(UUID groupId) {
    return groupRepository.findById(groupId)
        .switchIfEmpty(Mono.error(new RuntimeException("server not found")))
        .flatMap(groupRepository::delete);
  }

}
