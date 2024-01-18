package com.atowz.auth.infrastructure.jwt;

import com.atowz.auth.infrastructure.redis.RedisUtil;
import com.atowz.member.application.MemberQueryService;
import com.atowz.member.doamin.entity.Member;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProvider jwtProvider;
    private final RedisUtil redisUtil;
    private final MemberQueryService memberQueryService;

    private final long ACCESS_TOKEN_EXPIRED_IN = (1000L * 60) * 10; // 10 분
    private final long REFRESH_TOKEN_EXPIRED_IN = (1000L * 60 * 60 * 24) * 7; // 7 일


    public HttpHeaders createAccessTokenInHeader(Long memberId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", memberId);

        String accessToken = jwtProvider.getToken(claims, ACCESS_TOKEN_EXPIRED_IN);

        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken", accessToken);
        return headers;
    }

    public String createRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        String refreshToken = jwtProvider.getToken(claims, REFRESH_TOKEN_EXPIRED_IN);
        redisUtil.setData(username, refreshToken, REFRESH_TOKEN_EXPIRED_IN);
        return refreshToken;
    }

    public Member getMember(String accessToken) {
        String value = redisUtil.getValue(accessToken);

        if (value == null) {
            Long memberId = (long) (Integer) jwtProvider.getClaims(accessToken).get("memberId");
            return memberQueryService.byId(memberId);
        }

        throw new IllegalArgumentException("만료된 access token.");
    }

    public void isValid(String accessToken) {
        if (accessToken == null)
            throw new IllegalArgumentException("Authorization 이 없음");

        String value = redisUtil.getValue(accessToken);

        if (value != null) {
            throw new IllegalArgumentException("만료된 access token.");
        }
    }

    public String getRefreshToken(Cookie[] cookies) {
        for (Cookie cookie : cookies)
            if ("refreshToken".equals(cookie.getName()))
                return cookie.getValue();

        throw new IllegalArgumentException("refresh token 이 없음.");
    }

    public Member getMemberByRefreshToken(String refreshToken) {
        String username = (String) jwtProvider.getClaims(refreshToken).get("username");
        String value = redisUtil.getValue(username);

        if (!refreshToken.equals(value))
            throw new IllegalArgumentException("유효하지 않는 refresh token.");

        Optional<Member> byUsername = memberQueryService.byUsername(username);

        if (byUsername.isPresent())
            return byUsername.get();

        throw new IllegalStateException("존재하지 않는 username.");
    }

    public void expireToken(String accessToken, String refreshToken) {
        redisUtil.setData(accessToken, "is expired", ACCESS_TOKEN_EXPIRED_IN);
        redisUtil.deleteData((String) jwtProvider.getClaims(refreshToken).get("username"));
    }
}
