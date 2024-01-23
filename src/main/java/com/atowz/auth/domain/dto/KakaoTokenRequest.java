package com.atowz.auth.domain.dto;

import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoTokenRequest {

    @FormProperty("grant_type")
    private String grantType;
    @FormProperty("client_id")
    private String clientId;
    @FormProperty("redirect_uri")
    private String redirectUri;
    @FormProperty("client_secret")
    private String clientSecret;
    private String code;
}