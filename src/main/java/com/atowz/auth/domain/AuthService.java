package com.atowz.auth.domain;

import com.atowz.auth.domain.dto.KakaoTokenReqDto;
import com.atowz.global.feign.client.KakaoTokenClient;
import com.atowz.global.feign.client.KakaoUserClient;
import com.atowz.global.feign.dto.KakaoTokenResDto;
import com.atowz.global.feign.dto.KakaoUserResDto;
import com.atowz.global.feign.dto.UserResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final KakaoTokenClient tokenClient;
    private final KakaoUserClient userClient;

    @Value("${kakao.request.grant_type}")
    private String grantType;
    @Value("${kakao.request.client_id}")
    private String clientId;
    @Value("${kakao.request.redirect_uri}")
    private String redirectUri;
    @Value("${kakao.request.client_secret}")
    private String clientSecret;

    private final String KAKAO_TOKEN_TYPE = "Bearer ";

    public String getToken(String code) {
        KakaoTokenReqDto reqDto = new KakaoTokenReqDto(grantType, clientId, redirectUri, clientSecret, code);

        KakaoTokenResDto resDto = tokenClient.getToken(reqDto);
        return resDto.getAccess_token();
    }

    public UserResDto getUser(String accessToken) {
        KakaoUserResDto resDto = userClient.getUser(KAKAO_TOKEN_TYPE + accessToken);
        UserResDto userDto = resDto.getProperties();
        userDto.addUsername(resDto.getId());
        return userDto;
    }
}
