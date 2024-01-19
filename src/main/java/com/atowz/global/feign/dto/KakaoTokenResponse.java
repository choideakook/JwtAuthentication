package com.atowz.global.feign.dto;

import lombok.Getter;

@Getter
public class KakaoTokenResponse {

    private String token_type;
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String refresh_token_expires_in;

    public void addKakaoAccessToken(String kakaoAccessToken) {
        this.access_token = kakaoAccessToken;
    }
}
