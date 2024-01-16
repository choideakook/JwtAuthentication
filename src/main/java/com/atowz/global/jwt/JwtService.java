package com.atowz.global.jwt;

import com.atowz.global.feign.dto.UserResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProvider jwtProvider;
    private final long ATK_EXPIRED_IN = (1000L * 60) * 10;
    private final long RTK_EXPIRED_IN = (1000L * 60 * 60 * 24) * 7;


    public String createAtk(UserResDto user) {
        return "";
    }
}
