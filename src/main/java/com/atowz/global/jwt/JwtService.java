package com.atowz.global.jwt;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProvider jwtProvider;
    private final long ATK_EXPIRED_IN = (1000L * 60) * 10;
    private final long RTK_EXPIRED_IN = (1000L * 60 * 60 * 24) * 7;
    private final int COOKIE_EXPIRED_IN = (60 * 60 * 24) * 7;


    public HttpHeaders createAtkInHeader(Long memberId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", memberId);

        String atk = jwtProvider.getToken(claims, ATK_EXPIRED_IN);

        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken", atk);
        return headers;
    }

    public String createRtkInCookie(Long memberId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", memberId);

        return jwtProvider.getToken(claims, RTK_EXPIRED_IN);

//        Cookie cookie = new Cookie("refreshToken", rtk);
//        cookie.setMaxAge(COOKIE_EXPIRED_IN);
//        cookie.setPath("/");
//        return cookie;
    }
}
