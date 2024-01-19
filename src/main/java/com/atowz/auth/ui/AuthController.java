package com.atowz.auth.ui;

import com.atowz.auth.domain.AuthService;
import com.atowz.auth.domain.dto.TokenRequest;
import com.atowz.auth.infrastructure.jwt.JwtService;
import com.atowz.global.argumentResolver.accessTokenToMember.AccessTokenToMember;
import com.atowz.global.argumentResolver.getToken.GetToken;
import com.atowz.global.argumentResolver.refreshTokenToMember.RefreshTokenToMember;
import com.atowz.global.feign.dto.UserResponse;
import com.atowz.member.application.MemberService;
import com.atowz.member.doamin.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;
    private final JwtService jwtService;

    @GetMapping("/kakao")
    public ResponseEntity kakaoLogin(@RequestParam("code") String code) {
        log.info("kakao login 요청 확인");

        UserResponse user = authService.getUser(code);
        Member member = memberService.whenKakaoLogin(user);
        HttpHeaders headers = jwtService.createTokenInHeader(member);

        log.info("login 성공 / member id = {}", member.getId());
        return ResponseEntity.noContent()
                .headers(headers)
                .build();
    }

    @GetMapping("/reissue-token")
    public ResponseEntity reissueToken(@RefreshTokenToMember Member member) {
        log.info("token 재발급 요청 확인");

        HttpHeaders headers = jwtService.createTokenInHeader(member);

        log.info("token 재발급 성공 / member id = {}", member.getId());
        return ResponseEntity.noContent()
                .headers(headers)
                .build();
    }

    @GetMapping("/logout")
    public ResponseEntity logout(@GetToken TokenRequest tokenDto, @AccessTokenToMember Member member) {
        log.info("logout 요청 확인");

        jwtService.expireToken(tokenDto);

        log.info("logout 완료 / member id = {}", member.getId());
        return ResponseEntity.noContent()
                .build();
    }
}