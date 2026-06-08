package com.chat.vortex.group.repository;

import org.springframework.stereotype.Repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.chat.vortex.group.model.Member;
import reactor.core.publisher.Flux;
import com.chat.vortex.group.model.Role;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, String> {
  Flux<Member> findByGroupId(UUID groupId);

  Flux<Member> findByUserId(UUID userId);

  Mono<Member> findByUserIdAndGroupId(UUID groupId, UUID userId);
}
