package com.atowz.member.application;

import com.atowz.global.feign.dto.UserResponse;
import com.atowz.member.doamin.entity.Member;
import com.atowz.member.infrastructure.MemberJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("단위 : 회원 서비스")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberJpaRepository memberRepository;

    @DisplayName("카카오 인증")
    @Nested
    class whenKakoLogin {
        @DisplayName("로그인 성공")
        @Test
        void no1() {
            findByUsernameMock();

            String username = "user1";
            Member member = memberService.whenKakaoLogin(getUserResponse(username));

            assertThat(member.getUsername()).isEqualTo(username);
        }

        @DisplayName("회원가입 성공")
        @Test
        void no2() {
            findByUsernameMock();
            createMemberMock();

            String username = "user2";
            UserResponse user2 = getUserResponse(username);

            Member member = memberService.whenKakaoLogin(user2);

            assertThat(member.getUsername()).isEqualTo(username);
        }
    }

    @DisplayName("회원 객체 생성")
    @Test
    void no3() {
        createMemberMock();

        String username = "user3";
        Member member = memberService.createMember(getUserResponse(username));

        assertThat(member.getUsername()).isEqualTo(username);
    }

    private Member createMember(String username) {
        return Member.builder()
                .id(1L)
                .username(username)
                .nickname("member1")
                .build();
    }

    private UserResponse getUserResponse(String username) {
        return new UserResponse(username, "member1", "");
    }

    private void findByUsernameMock() {
        when(memberRepository.findByUsername(anyString()))
                .thenAnswer(invocation -> {
                    String username = invocation.getArgument(0);

                    return username.equals("user1") ? Optional.of(createMember(username)) : Optional.empty();
                });
    }

    private void createMemberMock() {
        when(memberRepository.save(any()))
                .thenAnswer(invocation -> {
                    Member member = invocation.getArgument(0);
                    return member;
                });
    }
}