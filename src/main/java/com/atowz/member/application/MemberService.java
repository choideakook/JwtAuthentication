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

    private final int RECOMMEND_CODE_LENGTH = 8;

    public Member whenKakaoLogin(UserResponse response) {
        return this.findByUsername(response.getUsername())
                .orElseGet(() -> this.createMember(response));
    }

    public Member createMember(UserResponse response) {
        Member member = Member.createKakaoMember(
                response.getUsername(),
                response.getNickname(),
                response.getProfileImage(),
                getRecommendCode());

        return memberJpaRepository.save(member);
    }

    private String getRecommendCode() {
        String recommendCode = UUID.randomUUID()
                .toString()
                .substring(0, RECOMMEND_CODE_LENGTH);

        if (memberJpaRepository.existsByRecommendCode(recommendCode)) {
            return getRecommendCode();
        }
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
