package com.atowz.auth.ui;

import com.atowz.global.feign.client.KakaoTokenClient;
import com.atowz.global.feign.client.KakaoUserClient;
import com.atowz.global.feign.dto.KakaoTokenResponse;
import com.atowz.global.feign.dto.KakaoUserResponse;
import com.atowz.global.feign.dto.UserResponse;
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
        KakaoTokenResponse resDto = new KakaoTokenResponse();
        resDto.addKakaoAccessToken("kakao access token");

        when(kakaoTokenClient.getToken(any()))
                .thenReturn(resDto);
    }

    public void requestUserMocking() {
        UserResponse user = new UserResponse(null, "user1", "img");
        KakaoUserResponse resDto = new KakaoUserResponse(1234L, user);

        when(kakaoUserClient.getUser(eq("Bearer kakao access token")))
                .thenReturn(resDto);
    }
}
