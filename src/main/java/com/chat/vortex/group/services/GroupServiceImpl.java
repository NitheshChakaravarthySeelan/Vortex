package com.chat.vortex.group.services;

import com.chat.vortex.group.model.GroupServer;
import com.chat.vortex.group.repository.GroupServerRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupServerRepository groupRepository;

    @Override
    public Mono<GroupServer> createGroup(UUID ownerId, String groupName) {
        GroupServer newGroup = new GroupServer(
            UUID.randomUUID(),
            groupName,
            ownerId
        );
        return groupRepository.save(newGroup);
    }

    @Override
    public Mono<GroupServer> getServerById(UUID groupId) {
        return groupRepository
            .findById(groupId)
            .switchIfEmpty(
                Mono.error(new RuntimeException("the group is not there"))
            );
    }

    @Override
    public Flux<GroupServer> getAllServers() {
        return groupRepository.findAll();
    }

    @Override
    public Mono<Void> removeGroup(UUID groupId) {
        return groupRepository
            .findById(groupId)
            .switchIfEmpty(Mono.error(new RuntimeException("server not found")))
            .flatMap(groupRepository::delete);
    }
}
