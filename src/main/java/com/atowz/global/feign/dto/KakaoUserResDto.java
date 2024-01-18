package com.atowz.global.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserResDto {

    private Long id;
    private UserResDto properties;
}
