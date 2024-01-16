package com.atowz.auth.ui;

import com.atowz.auth.application.AuthService;
import com.atowz.global.feign.dto.UserResDto;
import com.atowz.global.jwt.JwtService;
import com.atowz.member.application.MemberQueryService;
import com.atowz.member.application.MemberService;
import com.atowz.member.doamin.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
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
        log.info("인가 코드 조회 성공 : " + code);

        String accessToken = authService.getToken(code);
        log.info(("kakao ATK 조회 성공 : " + accessToken));

        UserResDto user = authService.getUser(accessToken);
        Member member = getMember(user);

        HttpHeaders headers = jwtService.createAtkInHeader(member.getId());
        String rtk = jwtService.createRtk(member.getUsername());
        headers.add(
                HttpHeaders.SET_COOKIE,
                "refreshToken=" + rtk +
                        "; Path=/; Max-Age=" + (24 * 60 * 60 * 7));

        return ResponseEntity.noContent().headers(headers).build();
    }


    @GetMapping("/access-token")
    public ResponseEntity reissueToken(HttpServletRequest request) {
        request.getCookies()
    }

    private Member getMember(UserResDto user) {
        Optional<Member> byUsername = memberQueryService.byUsername(user.getId());

        if (byUsername.isPresent())
            return byUsername.get();

        return memberService.createMember(user);
    }
}
