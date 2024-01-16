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
    public Member byUsername(String username) {
        Optional<Member> byUsername = memberJpaRepository.findByUsername(username);

        if (byUsername.isPresent())
            return byUsername.get();

        throw new IllegalArgumentException("존재하지 않는 username");
    }
}
