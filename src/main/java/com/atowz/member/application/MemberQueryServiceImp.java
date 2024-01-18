package com.atowz.member.application;

import com.atowz.member.doamin.entity.Member;
import com.atowz.member.infrastructure.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberQueryServiceImp implements MemberQueryService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberJpaRepository.findByUsername(username);
    }

    @Override
    public Member findById(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 id"));
    }
}
