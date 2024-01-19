package com.atowz.auth.domain;

import com.atowz.auth.domain.dto.KakaoTokenRequest;
import com.atowz.global.feign.client.KakaoTokenClient;
import com.atowz.global.feign.client.KakaoUserClient;
import com.atowz.global.feign.dto.KakaoUserResponse;
import com.atowz.global.feign.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final KakaoTokenClient tokenClient;
    private final KakaoUserClient userClient;

    @Value("${kakao.request.grant_type}")
    private String grantType;
    @Value("${kakao.request.client_id}")
    private String clientId;
    @Value("${kakao.request.redirect_uri}")
    private String redirectUri;
    @Value("${kakao.request.client_secret}")
    private String clientSecret;

    private final String KAKAO_TOKEN_TYPE = "Bearer ";

    public UserResponse getUser(String code) {
        KakaoTokenRequest request = new KakaoTokenRequest(grantType, clientId, redirectUri, clientSecret, code);

        String accessToken = tokenClient.getToken(request).getAccessToken();
        KakaoUserResponse response = userClient.getUser(KAKAO_TOKEN_TYPE + accessToken);

        UserResponse userResponse = response.getProperties();
        userResponse.addUsername(response.getId());
        return userResponse;
    }
}
