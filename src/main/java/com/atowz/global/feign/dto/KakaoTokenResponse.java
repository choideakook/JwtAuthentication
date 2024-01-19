package com.atowz.global.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoTokenResponse {

    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("refresh_token_expires_in")
    private String refreshTokenExpiresIn;

    public void addKakaoAccessToken(String kakaoAccessToken) {
        this.accessToken = kakaoAccessToken;
    }
}
