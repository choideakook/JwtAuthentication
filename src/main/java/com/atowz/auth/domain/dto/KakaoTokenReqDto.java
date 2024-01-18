package com.atowz.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoTokenReqDto {

    private String grant_type;
    private String client_id;
    private String redirect_uri;
    private String client_secret;
    private String code;
}