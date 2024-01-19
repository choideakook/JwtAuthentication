package com.atowz.auth.ui;

import com.atowz.auth.infrastructure.jwt.JwtService;
import com.atowz.auth.infrastructure.redis.RedisUtil;
import com.atowz.member.application.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@DisplayName("통합 : 토큰 재발급")
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerReIssueTokenTest extends KakaoClientMock{

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
        createMember(mvc);
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    void no1() {

    }
}