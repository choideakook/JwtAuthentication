package com.atowz.global.feign.dto;

import lombok.Data;

@Data
public class KakaoTokenResDto {

    private String token_type;
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String refresh_token_expires_in;
}
