package com.atowz.auth.ui;

import com.atowz.auth.application.AuthService;
import com.atowz.global.feign.dto.UserResDto;
import com.atowz.global.jwt.JwtService;
import com.atowz.member.application.MemberQueryService;
import com.atowz.member.application.MemberService;
import com.atowz.member.doamin.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final MemberService memberService;
    private final MemberQueryService memberQueryService;
    private final JwtService jwtService;

    @GetMapping("/kakao")
    public ResponseEntity kakaoLogin(@RequestParam("code") String code) {
        log.info("login 요청 확인 / 인가 code = {}", code);

        String accessToken = authService.getToken(code);
        log.info("kakao ATK 조회 성공 : {}", accessToken);

        UserResDto user = authService.getUser(accessToken);
        Member member = getMember(user);
        HttpHeaders headers = getHeaders(member);

        log.info("login 성공 / member id = {}", member.getId());
        return ResponseEntity.noContent().headers(headers).build();
    }


    @GetMapping("/reissue-token")
    public ResponseEntity reissueToken(HttpServletRequest request) {
        log.info("token 재발급 요청 확인");

        String rtk = jwtService.getRefreshToken(request.getCookies());
        Member member = jwtService.getMemberByRtk(rtk);
        HttpHeaders headers = getHeaders(member);

        log.info("token 재발급 성공 / member id = {}", member.getId());
        return ResponseEntity.noContent().headers(headers).build();
    }

    @GetMapping("/logout")
    public ResponseEntity logout(
            @RequestHeader("Authorization") String atk,
            HttpServletRequest request
    ) {
        log.info("logout 요청 확인");

        String rtk = jwtService.getRefreshToken(request.getCookies());
        Member member = jwtService.getMember(atk);
        jwtService.expireToken(atk, rtk);

        log.info("logout 완료 / member id = {}", member.getId());
        return ResponseEntity.noContent().build();
    }



    private HttpHeaders getHeaders(Member member) {
        HttpHeaders headers = jwtService.createAtkInHeader(member.getId());
        String rtk = jwtService.createRtk(member.getUsername());
        headers.add(
                HttpHeaders.SET_COOKIE,
                "refreshToken=" + rtk +
                        "; Path=/; Max-Age=" + (24 * 60 * 60 * 7));
        return headers;
    }

    private Member getMember(UserResDto user) {
        Optional<Member> byUsername = memberQueryService.byUsername(user.getId());

        if (byUsername.isPresent())
            return byUsername.get();

        return memberService.createMember(user);
    }
}
