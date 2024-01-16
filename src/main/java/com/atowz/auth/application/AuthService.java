package com.atowz.auth.application;

import com.atowz.auth.application.dto.KakaoTokenReqDto;
import com.atowz.global.feign.client.KakaoClient;
import com.atowz.global.feign.dto.response.KakaoTokenResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private KakaoTokenReqDto reqDto;
    private final KakaoClient kakaoClient;

    public String getToken(String code) {
        reqDto.setCode(code);

        KakaoTokenResDto resDto = kakaoClient.getToken(reqDto);
        return resDto.getAccess_token();
    }
}
