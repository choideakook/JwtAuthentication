package com.atowz.auth.ui;

import com.atowz.auth.application.AuthService;
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

    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam("code") String code) {
        log.info("code : " + code);

        String accessToken = authService.getToken(code);
        log.info(("ATK : " + accessToken));
    }
}
