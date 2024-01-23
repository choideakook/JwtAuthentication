package com.atowz.auth.ui;

import com.atowz.auth.infrastructure.jwt.JwtService;
import com.atowz.auth.infrastructure.redis.RedisUtil;
import com.atowz.global.feign.dto.UserResponse;
import com.atowz.member.application.MemberService;
import com.atowz.member.doamin.entity.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.atowz.auth.ui.AuthBaseTest.setRefreshTokenToRedis;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("통합 : kakao api 인증")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AuthControllerKakaoLoginTest extends KakaoClientMock {

    @Autowired
    MockMvc mvc;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JwtService jwtService;
    @Autowired
    MemberService memberService;

    @BeforeEach
    void setup() {
        requestUserMocking();
        requestTokenMocking();
    }

    @Test
    @DisplayName("kakao api 로 회원가입 성공")
    void no1() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/auth/kakao")
                        .contentType(APPLICATION_JSON)
                        .param("code", "auth Code")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(cookie().exists("refreshToken"))
                .andExpect(header().exists("accessToken"));

        String refreshToken = redisUtil.getValue("1234");
        assertThat(refreshToken).isNotEmpty();
    }

    @Test
    @DisplayName("kakao api 로 로그인 성공")
    void no2() throws Exception {
        setRefreshTokenToRedis(redisUtil, "old refresh token");

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/auth/kakao")
                        .contentType(APPLICATION_JSON)
                        .param("code", "auth Code")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(cookie().exists("refreshToken"))
                .andExpect(header().exists("accessToken"));

        String refreshToken = redisUtil.getValue("1234");
        assertThat(refreshToken).isNotEqualTo("old refresh token");
    }
}