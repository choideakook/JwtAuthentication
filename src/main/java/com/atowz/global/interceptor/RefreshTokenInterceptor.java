package com.atowz.global.interceptor;

import com.atowz.auth.infrastructure.jwt.JwtService;
import com.atowz.global.exception.jwt.InvalidJwtException;
import com.atowz.global.exception.ui.ErrorStatus;
import com.atowz.member.doamin.entity.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static com.atowz.global.exception.ui.ErrorStatus.JWT_INVALID;
import static com.atowz.global.exception.ui.ErrorStatus.JWT_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;
    private final String refreshTokenKey = "refreshToken";
    private final String memberKey = "member";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String refreshToken = getRefreshToken(cookies);
        Member member = jwtService.getMemberAndValidationCheck(refreshToken);

        request.setAttribute(memberKey, member);
        request.setAttribute(refreshTokenKey, refreshToken);

        log.info("refresh token 유효성 검사 완료");
        return true;
    }

    public String getRefreshToken(Cookie[] cookies) {
        return Optional.ofNullable(cookies)
                .map(Arrays::stream)
                .orElseGet(Stream::empty)
                .filter(cookie -> refreshTokenKey.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() ->
                        new InvalidJwtException(JWT_NOT_FOUND));
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
