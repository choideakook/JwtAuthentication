package com.atowz.member.application;

import com.atowz.global.feign.dto.UserResDto;
import com.atowz.member.doamin.entity.Member;
import com.atowz.member.infrastructure.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImp implements MemberService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member createMember(UserResDto dto) {
        Member member = Member.createMember(dto, getRecommendCode());
        return memberJpaRepository.save(member);
    }


    private String getRecommendCode() {
        int recommendCodeLength = 8;

        String recommendCode = UUID.randomUUID()
                .toString()
                .substring(0, recommendCodeLength);

        if (memberJpaRepository.existsByRecommendCode(recommendCode))
            return getRecommendCode();

        return recommendCode;
    }
}
