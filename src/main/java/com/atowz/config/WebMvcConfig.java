package com.atowz.config;

import com.atowz.auth.infrastructure.jwt.JwtService;
import com.atowz.global.interceptor.AccessTokenInterceptor;
import com.atowz.global.interceptor.RefreshTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtService jwtService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessTokenInterceptor(jwtService))
                .addPathPatterns("/**");
        registry.addInterceptor(new RefreshTokenInterceptor(jwtService))
                .addPathPatterns("/api/auth/reissue-token", "/api/auth/logout");
    }
}
