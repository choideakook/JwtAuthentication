package com.atowz.auth.ui;

import com.atowz.auth.infrastructure.jwt.JwtService;
import com.atowz.auth.infrastructure.redis.RedisUtil;
import com.atowz.global.feign.dto.UserResponse;
import com.atowz.member.application.MemberService;
import com.atowz.member.doamin.entity.Member;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;

public class AuthBaseTest {

    private static final long REFRESH_TOKEN_EXPIRED_IN = (1000L * 60 * 60 * 24) * 7;

    static Member createMember(MemberService memberService) {
        return memberService.createMember(
                new UserResponse(
                        "1234",
                        "user1",
                        "img"));
    }

    static HttpHeaders getHeaderInAccessToken(JwtService jwtService, Member member) {
        HttpHeaders headers = jwtService.createTokenInHeader(member);
        headers.add("Authorization", headers.getFirst("accessToken"));
        return headers;
    }

    static Cookie getCookieByUsername(JwtService jwtService, RedisUtil redisUtil, String username) {
        String rtk = jwtService.createRefreshToken(username);
        redisUtil.setData(username, rtk, REFRESH_TOKEN_EXPIRED_IN);
        return new Cookie("refreshToken", rtk);
    }

    static void setRefreshTokenToRedis(RedisUtil redisUtil, String testToken) {
        redisUtil.setData("1234", testToken, REFRESH_TOKEN_EXPIRED_IN);
    }
}
