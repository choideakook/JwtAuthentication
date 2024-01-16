package com.atowz.auth.application;

import com.atowz.auth.application.dto.KakaoTokenReqDto;
import com.atowz.global.feign.client.KakaoTokenClient;
import com.atowz.global.feign.client.KakaoUserClient;
import com.atowz.global.feign.dto.KakaoTokenResDto;
import com.atowz.global.feign.dto.KakaoUserResDto;
import com.atowz.global.feign.dto.UserResDto;
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
    private final KakaoTokenClient tokenClient;
    private final KakaoUserClient userClient;

    public String getToken(String code) {
        reqDto.setCode(code);

        KakaoTokenResDto resDto = tokenClient.getToken(reqDto);
        return resDto.getAccess_token();
    }

    public UserResDto getUser(String atk) {
        KakaoUserResDto resDto = userClient.getUser("Bearer " + atk);
        UserResDto userDto = resDto.getProperties();
        userDto.setId(resDto.getId());
        return userDto;
    }
}
