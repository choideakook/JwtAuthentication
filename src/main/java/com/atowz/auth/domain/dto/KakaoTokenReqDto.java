package com.atowz.auth.domain.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class KakaoTokenReqDto {

    @Value("${kakao.request.grant_type}")
    private String grant_type;
    @Value("${kakao.request.client_id}")
    private String client_id;
    @Value("${kakao.request.redirect_uri}")
    private String redirect_uri;
    @Value("${kakao.request.client_secret}")
    private String client_secret;
    private String code;

    public void addCode(String code) {
        this.code = code;
    }
}