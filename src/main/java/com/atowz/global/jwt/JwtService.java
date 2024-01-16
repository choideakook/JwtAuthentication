package com.atowz.global.jwt;

import com.atowz.global.redis.RedisUtil;
import com.atowz.member.application.MemberQueryService;
import com.atowz.member.doamin.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProvider jwtProvider;
    private final RedisUtil redisUtil;
    private final MemberQueryService memberQueryService;

    private final long ATK_EXPIRED_IN = (1000L * 60) * 10; // 10 분
    private final long RTK_EXPIRED_IN = (1000L * 60 * 60 * 24) * 7; // 7 일


    public HttpHeaders createAtkInHeader(Long memberId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", memberId);

        String atk = jwtProvider.getToken(claims, ATK_EXPIRED_IN);

        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken", atk);
        return headers;
    }

    public String createRtk(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        String rtk = jwtProvider.getToken(claims, RTK_EXPIRED_IN);
        redisUtil.setData(username, rtk, RTK_EXPIRED_IN);
        return rtk;
    }

    public Member getMember(String accessToken) {
        Long memberId = (Long) jwtProvider.getClaims(accessToken).get("memberId");
        return memberQueryService.byId(memberId);
    }
}
