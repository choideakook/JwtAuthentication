package com.atowz.auth.ui;

import com.atowz.global.feign.dto.UserResDto;
import com.atowz.auth.infrastructure.jwt.JwtService;
import com.atowz.auth.infrastructure.redis.RedisUtil;
import com.atowz.member.application.MemberService;
import com.atowz.member.doamin.entity.Member;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("통합 : logout")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AuthControllerLogoutTest {

    @Autowired MockMvc mvc;
    @Autowired RedisUtil redisUtil;
    @Autowired JwtService jwtService;
    @Autowired MemberService memberService;

    @AfterEach
    void rollback() {
        redisUtil.deleteData("1234");
    }

    @Test
    @DisplayName("logout 성공")
    void no1() throws Exception {
        Member member = createMember();
        HttpHeaders headers = getHeader(member.getId());
        Cookie cookie = getCookie(member.getUsername());

        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .get("/api/auth/logout")
                .contentType(APPLICATION_JSON)
                .headers(headers)
                .cookie(cookie)
        ).andDo(print());

        result.andExpect(status().is2xxSuccessful());
    }

    private Member createMember() {
        return memberService.createMember(
                new UserResDto(
                        "1234",
                        "user1",
                        "img"));
    }

    private HttpHeaders getHeader(Long memberId) {
        HttpHeaders headers = jwtService.createAccessTokenInHeader(memberId);
        headers.add("Authorization", headers.getFirst("accessToken"));
        return headers;
    }

    private Cookie getCookie(String username) {
        String rtk = jwtService.createRefreshToken(username);
        redisUtil.setData(username, rtk, (1000L * 60 * 60 * 24) * 7);
        return new Cookie("refreshToken", rtk);
    }
}