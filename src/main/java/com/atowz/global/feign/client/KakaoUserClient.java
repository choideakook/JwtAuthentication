package com.atowz.global.feign.client;

import com.atowz.global.feign.dto.KakaoUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoUser", url = "${kakao.feign.user_url}")
public interface KakaoUserClient {

    @GetMapping(value = "${kakao.feign.req_user}", consumes = "application/x-www-form-urlencoded")
    KakaoUserResponse getUser(@RequestHeader("Authorization") String atk);
}