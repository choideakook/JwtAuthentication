package com.atowz.global.feign.client;

import com.atowz.auth.application.dto.KakaoTokenReqDto;
import com.atowz.global.feign.dto.response.KakaoTokenResDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kakao", url = "${kakao.feign.url}")
public interface KakaoClient {

    @PostMapping(value = "${kakao.feign.req_token}", consumes = "application/x-www-form-urlencoded")
    KakaoTokenResDto getToken(@RequestBody KakaoTokenReqDto body);
}
