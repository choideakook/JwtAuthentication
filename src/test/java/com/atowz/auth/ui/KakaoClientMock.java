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
        resDto.setAccess_token("kakao access token");

        when(kakaoTokenClient.getToken(any()))
                .thenReturn(resDto);
    }

    public void requestUserMocking() {
        KakaoUserResDto resDto = new KakaoUserResDto();
        UserResDto user = new UserResDto();
        user.setNickname("user1");
        user.setProfile_image("img");
        resDto.setId(1234L);
        resDto.setProperties(user);

        when(kakaoUserClient.getUser(eq("Bearer kakao access token")))
                .thenReturn(resDto);
    }
}
