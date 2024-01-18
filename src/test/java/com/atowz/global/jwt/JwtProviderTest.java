package com.atowz.global.jwt;

import com.atowz.auth.infrastructure.jwt.JwtProvider;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayName("JWT 테스트")
@SpringBootTest
class JwtProviderTest {

    @Autowired
    JwtProvider jwtProvider;

    @Test
    @DisplayName("JWT 생성")
    void no1() {
        Map<String, Object> claims = createClaims();

        String jwt = jwtProvider.getToken(claims, 60 * 10);

        assertThat(jwt).isNotEmpty();
    }

    @Test
    @DisplayName("JWT 복호화")
    void no2() {
        Map<String, Object> claims = createClaims();
        String jwt = jwtProvider.getToken(claims, 60 * 10);

        Map<String, Object> result = jwtProvider.getClaims(jwt);

        assertSoftly(softly -> {
            softly.assertThat(result.get("id")).isEqualTo(1);
            softly.assertThat(result.get("username")).isEqualTo("user");
        });
    }


    private static Map<String, Object> createClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "user");
        return claims;
    }
}