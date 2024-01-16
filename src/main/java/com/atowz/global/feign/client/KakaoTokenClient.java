package com.atowz.global.feign.client;

import com.atowz.auth.application.dto.KakaoTokenReqDto;
import com.atowz.global.feign.dto.response.KakaoTokenResDto;
import com.atowz.global.feign.dto.response.KakaoUserResDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoToken", url = "${kakao.feign.token_url}")
public interface KakaoTokenClient {

    @PostMapping(value = "${kakao.feign.req_token}", consumes = "application/x-www-form-urlencoded")
    KakaoTokenResDto getToken(@RequestBody KakaoTokenReqDto body);
}
