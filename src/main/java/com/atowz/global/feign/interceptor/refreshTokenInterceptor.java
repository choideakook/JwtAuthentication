package com.atowz.global.feign.interceptor;

import com.atowz.auth.infrastructure.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String refreshToken = getRefreshToken(cookies);
        jwtService.isRefreshTokenValid(refreshToken);

        log.info("refresh token 유효성 검사 완료");
        return true;
    }

    public String getRefreshToken(Cookie[] cookies) {
        for (Cookie cookie : cookies)
            if ("refreshToken".equals(cookie.getName()))
                return cookie.getValue();

        throw new IllegalArgumentException("refresh token 이 없음.");
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
