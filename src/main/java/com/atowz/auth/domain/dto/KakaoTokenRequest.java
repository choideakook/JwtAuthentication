package com.atowz.auth.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoTokenRequest {

    private String code;

    @JsonProperty("grant_type")
    private String grantType;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("redirect_uri")
    private String redirectUri;
    @JsonProperty("client_secret")
    private String clientSecret;
}