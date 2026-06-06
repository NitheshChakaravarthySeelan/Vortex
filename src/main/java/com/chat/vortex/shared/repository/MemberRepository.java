package com.chat.vortex.shared.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.chat.vortex.shared.model.Member;
import reactor.core.publisher.Flux;
import com.chat.vortex.shared.model.Role;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, String> {
  Flux<Member> findByRoles(Role role);
}
