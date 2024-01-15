package com.atowz.auth.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam("code") String code) {
        log.info("code : " + code);
    }
}
