package com.atowz.auth.domain.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kakao.req")
public class KakaoTokenReqDto {

    private String grant_type;
    private String client_id;
    private String redirect_uri;
    private String client_secret;
    private String code;
}