package com.atowz.member.application;

import com.atowz.global.feign.dto.UserResDto;
import com.atowz.member.doamin.entity.Member;
import com.atowz.member.infrastructure.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryServiceImp implements MemberQueryService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> byUsername(String username) {
        return memberJpaRepository.findByUsername(username);
    }

    @Override
    public Member byId(Long memberId) {
        Optional<Member> byId = memberJpaRepository.findById(memberId);

        if (byId.isPresent())
            return byId.get();

        throw new IllegalArgumentException("존재하지 않는 id");
    }
}
