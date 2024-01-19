package com.atowz.auth.ui;

import com.atowz.auth.infrastructure.jwt.JwtService;
import com.atowz.auth.infrastructure.redis.RedisUtil;
import com.atowz.member.application.MemberService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.atowz.global.exception.ui.ErrorStatus.JWT_INVALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("통합 : 토큰 재발급")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AuthControllerReIssueTokenTest extends KakaoClientMock {

    @Autowired
    MockMvc mvc;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JwtService jwtService;
    @Autowired
    MemberService memberService;

    @BeforeEach
    void setup() throws Exception {
        requestUserMocking();
        requestTokenMocking();
        kakaoLogin(mvc);
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    void no1() throws Exception {
        String oldRefreshToken = redisUtil.getValue("1234");
        Thread.sleep(1000L);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/auth/reissue-token")
                        .contentType(APPLICATION_JSON)
                        .cookie(new Cookie("refreshToken", oldRefreshToken))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(cookie().exists("refreshToken"))
                .andExpect(header().exists("accessToken"));

        String newRefreshToken = redisUtil.getValue("1234");
        assertThat(newRefreshToken).isNotEqualTo(oldRefreshToken);
    }

    @Test
    @DisplayName("refresh token 이 만료된 경우")
    void no2() throws Exception {
        String refreshToken = redisUtil.getValue("1234");
        redisUtil.deleteData("1234");

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/auth/reissue-token")
                        .contentType(APPLICATION_JSON)
                        .cookie(new Cookie("refreshToken", refreshToken))
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("errorMsg")
                        .value(JWT_INVALID.getErrorMessage()));
    }
}