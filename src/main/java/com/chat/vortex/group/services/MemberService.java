package com.chat.vortex.group.services;

import com.chat.vortex.group.model.Member;
import com.chat.vortex.group.model.Role;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface MemberService {
    Mono<Member> createMember(
        UUID memberId,
        UUID userId,
        Role role,
        UUID groupId
    );

    Mono<Void> removeMember(UUID userId, UUID groupId);
}
