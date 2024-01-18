package com.atowz.auth.ui;

import com.atowz.global.feign.client.KakaoTokenClient;
import com.atowz.global.feign.client.KakaoUserClient;
import com.atowz.global.feign.dto.KakaoTokenResDto;
import com.atowz.global.feign.dto.KakaoUserResDto;
import com.atowz.global.feign.dto.UserResDto;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class KakaoClientMock {

    @MockBean
    private KakaoTokenClient kakaoTokenClient;

    @MockBean
    private KakaoUserClient kakaoUserClient;

    public void requestTokenMocking() {
        KakaoTokenResDto resDto = new KakaoTokenResDto();
        resDto.addKakaoAccessToken("kakao access token");

        when(kakaoTokenClient.getToken(any()))
                .thenReturn(resDto);
    }

    public void requestUserMocking() {
        UserResDto user = new UserResDto(null, "user1", "img");
        KakaoUserResDto resDto = new KakaoUserResDto(1234L, user);

        when(kakaoUserClient.getUser(eq("Bearer kakao access token")))
                .thenReturn(resDto);
    }
}
