package com.atowz.member.application;

import com.atowz.global.feign.dto.UserResDto;
import com.atowz.member.doamin.entity.Member;
import com.atowz.member.infrastructure.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService {

    private final MemberJpaRepository memberJpaRepository;


    @Override
    public Member createMember(UserResDto dto) {
        Member member = Member.createMember(dto, getRecommendCode());
        return memberJpaRepository.save(member);
    }


    private String getRecommendCode() {
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 7; i++)
                sb.append((int) (Math.random() * 10));

            if (!memberJpaRepository.existsByRecommendCode(sb.toString()))
                return sb.toString();
        }
    }
}
