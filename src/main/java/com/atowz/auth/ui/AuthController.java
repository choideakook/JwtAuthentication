package com.atowz.auth.ui;

import com.atowz.auth.application.AuthService;
import com.atowz.global.feign.dto.UserResDto;
import com.atowz.member.application.MemberQueryService;
import com.atowz.member.application.MemberService;
import com.atowz.member.doamin.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final MemberService memberService;
    private final MemberQueryService memberQueryService;

    private Member member;

    @GetMapping("/kakao")
    public void kakaoLogin(@RequestParam("code") String code) {
        log.info("인가 코드 조회 성공 : " + code);

        String accessToken = authService.getToken(code);
        log.info(("kakao ATK 조회 성공 : " + accessToken));

        UserResDto user = authService.getUser(accessToken);

        try {
            member = memberQueryService.byUsername(user.getId());
        } catch (IllegalArgumentException e) {
            member = memberService.createMember(user);
        } finally {

        }
    }
}
