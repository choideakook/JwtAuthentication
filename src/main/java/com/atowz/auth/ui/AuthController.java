package com.atowz.auth.ui;

import com.atowz.auth.domain.AuthService;
import com.atowz.auth.domain.dto.TokenReqDto;
import com.atowz.global.argumentResolver.accessTokenToMember.AccessTokenToMember;
import com.atowz.global.argumentResolver.getToken.GetToken;
import com.atowz.global.argumentResolver.refreshTokenToMember.RefreshTokenToMember;
import com.atowz.global.feign.dto.UserResDto;
import com.atowz.auth.infrastructure.jwt.JwtService;
import com.atowz.member.application.MemberQueryService;
import com.atowz.member.application.MemberService;
import com.atowz.member.doamin.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {


    private final AuthService authService;
    private final MemberService memberService;
    private final MemberQueryService memberQueryService;
    private final JwtService jwtService;

    @GetMapping("/kakao")
    public ResponseEntity kakaoLogin(@RequestParam("code") String code) {
        log.info("login 요청 확인 / 인가 code = {}", code);

        String accessToken = authService.getToken(code);
        log.info("kakao accessToken 조회 성공 : {}", accessToken);

        UserResDto user = authService.getUser(accessToken);
        Member member = getMember(user);
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
    public ResponseEntity logout(@GetToken TokenReqDto tokenDto, @AccessTokenToMember Member member) {
        log.info("logout 요청 확인");

        jwtService.expireToken(tokenDto);

        log.info("logout 완료 / member id = {}", member.getId());
        return ResponseEntity.noContent()
                .build();
    }

    private Member getMember(UserResDto user) {
        return memberQueryService.byUsername(user.getUsername())
                .orElse(memberService.createMember(user));
    }
}