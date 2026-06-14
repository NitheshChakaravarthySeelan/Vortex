package com.chat.vortex.group.repository;

import com.chat.vortex.group.model.Member;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository
    extends ReactiveCrudRepository<Member, String>
{
    Flux<Member> findByGroupId(UUID groupId);

    Flux<Member> findByUserId(UUID userId);

    Mono<Member> findByUserIdAndGroupId(UUID groupId, UUID userId);
}
