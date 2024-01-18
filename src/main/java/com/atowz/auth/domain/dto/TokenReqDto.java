package com.atowz.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenReqDto {

    private String accessToken;
    private String refreshToken;
}
