package com.chat.vortex.group.services;

import com.chat.vortex.group.model.Member;
import com.chat.vortex.group.model.Role;
import com.chat.vortex.group.repository.MemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Mono<Member> createMember(
        UUID memberId,
        UUID userId,
        Role role,
        UUID groupId
    ) {
        Member newMember = new Member(memberId, userId, role, groupId);
        return memberRepository.save(newMember);
    }

    @Override
    public Mono<Void> removeMember(UUID userId, UUID groupId) {
        // Later we need to check if the user has appropriate role to remove.
        return memberRepository
            .findByUserIdAndGroupId(groupId, userId)
            .switchIfEmpty(Mono.error(new RuntimeException("Member not found")))
            .flatMap(memberRepository::delete);
    }
}
