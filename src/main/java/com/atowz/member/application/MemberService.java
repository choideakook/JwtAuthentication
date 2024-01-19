package com.atowz.member.application;

import com.atowz.global.feign.dto.UserResponse;
import com.atowz.member.doamin.entity.Member;
import com.atowz.member.infrastructure.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;

    public Member createMember(UserResponse dto) {
        Member member = Member.createKakaoMember(
                dto.getUsername(),
                dto.getNickname(),
                dto.getProfile_image(),
                getRecommendCode());

        return memberJpaRepository.save(member);
    }

    public Member whenKakaoLogin(UserResponse dto) {
        return this.findByUsername(dto.getUsername())
                .orElse(this.createMember(dto));
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

    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberJpaRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Member findById(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 id"));
    }
}
