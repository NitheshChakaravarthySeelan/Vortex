package com.chat.vortex.group.services;

import java.util.UUID;
import reactor.core.publisher.Mono;
import com.chat.vortex.group.model.Member;
import com.chat.vortex.group.model.Role;

public interface MemberService {
  Mono<Member> createMember(UUID userId, Role role, UUID groupId);

  Mono<Member> removeMember(UUID userId, UUID groupId);
}
