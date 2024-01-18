package com.atowz.config;

import com.atowz.global.argumentResolver.accessTokenToMember.AccessTokenArgumentResolver;
import com.atowz.global.argumentResolver.getToken.TokenArgumentResolver;
import com.atowz.global.argumentResolver.refreshTokenToMember.RefreshTokenArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AccessTokenArgumentResolver accessTokenArgumentResolver;
    private final RefreshTokenArgumentResolver refreshTokenArgumentResolver;
    private final TokenArgumentResolver tokenArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(accessTokenArgumentResolver);
        resolvers.add(refreshTokenArgumentResolver);
        resolvers.add(tokenArgumentResolver);
    }
}
