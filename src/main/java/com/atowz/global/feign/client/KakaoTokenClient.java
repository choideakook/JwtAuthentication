package com.atowz.global.feign.client;

import com.atowz.auth.domain.dto.KakaoTokenRequest;
import com.atowz.global.feign.dto.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kakaoToken", url = "${kakao.feign.token_url}")
public interface KakaoTokenClient {

    @PostMapping(value = "${kakao.feign.req_token}", consumes = "application/x-www-form-urlencoded")
    KakaoTokenResponse getToken(@RequestBody KakaoTokenRequest body);
}
