package com.atowz.auth.infrastructure.jwt;

import com.atowz.auth.domain.dto.TokenRequest;
import com.atowz.auth.infrastructure.redis.RedisUtil;
import com.atowz.global.exception.jwt.InvalidJwtException;
import com.atowz.member.application.MemberService;
import com.atowz.member.doamin.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.atowz.global.exception.ui.ErrorStatus.JWT_EXPIRED;
import static com.atowz.global.exception.ui.ErrorStatus.JWT_INVALID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProvider jwtProvider;
    private final RedisUtil redisUtil;
    private final MemberService memberService;

    @Value("${jwt.access-token-expired-in}")
    private long ACCESS_TOKEN_EXPIRED_IN;
    @Value("${jwt.refresh-token-expired-in}")
    private long REFRESH_TOKEN_EXPIRED_IN;


    public HttpHeaders createTokenInHeader(Member member) {
        String accessToken = createAccessToken(member.getId());
        String refreshToken = createRefreshToken(member.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken", accessToken);
        headers.add(
                HttpHeaders.SET_COOKIE,
                "refreshToken=" + refreshToken +
                        "; Path=/; Max-Age=" + REFRESH_TOKEN_EXPIRED_IN);
        return headers;
    }

    public String createAccessToken(Long memberId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", memberId);

        return jwtProvider.getToken(claims, ACCESS_TOKEN_EXPIRED_IN);
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
            return memberService.findById(memberId);
        }

        throw new InvalidJwtException(JWT_EXPIRED);
    }

    public void isAccessTokenValid(String accessToken) {
        String value = redisUtil.getValue(accessToken);

        if (value != null) {
            throw new InvalidJwtException(JWT_EXPIRED);
        }
    }

    public Member getMemberAndValidationCheck(String refreshToken) {
        String username = (String) jwtProvider.getClaims(refreshToken).get("username");
        String value = redisUtil.getValue(username);

        if (!refreshToken.equals(value))
            throw new InvalidJwtException(JWT_INVALID);

        return memberService.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 username."));
    }

    public void expireToken(TokenRequest tokenDto) {
        redisUtil.setData(tokenDto.getAccessToken(), "is expired", ACCESS_TOKEN_EXPIRED_IN);
        redisUtil.deleteData((String) jwtProvider
                .getClaims(tokenDto.getRefreshToken())
                .get("username"));
    }
}
