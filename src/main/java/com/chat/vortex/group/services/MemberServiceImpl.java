package com.chat.vortex.group.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chat.vortex.group.model.Member;
import com.chat.vortex.group.repository.MemberRepository;
import com.chat.vortex.group.model.Role;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;

  @Override
  public Mono<Member> createMember(UUID userId, Role role, UUID groupId) {
    Member newMember = new Member(userId, role, groupId);
    return memberRepository.save(newMember);
  }

  @Override
  public Mono<Member> removeMember(UUID userId, UUID groupId) {
    // Later we need to check if the user has appropriate role to remove.
    return memberRepository
        .findByUserIdAndGroupId(groupId, userId)
        .switchIfEmpty(Mono.error(new RuntimeException("Member not found")))
        .flatMap(memberRepository::delete);
  }
}
