package com.atowz.auth.ui;

import com.atowz.auth.infrastructure.jwt.JwtService;
import com.atowz.auth.infrastructure.redis.RedisUtil;
import com.atowz.member.application.MemberService;
import com.atowz.member.doamin.entity.Member;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.cookies.CookieDocumentation;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.atowz.auth.ui.AuthBaseTest.*;
import static com.atowz.global.exception.ui.ErrorStatus.JWT_INVALID;
import static com.atowz.global.exception.ui.ErrorStatus.JWT_NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("통합 : logout")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class AuthControllerLogoutTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JwtService jwtService;
    @Autowired
    MemberService memberService;


    @Test
    @DisplayName("logout 성공")
    void no1() throws Exception {
        Member member = createMember(memberService);
        HttpHeaders headers = getHeaderInAccessToken(jwtService, member);
        Cookie cookie = getCookieByUsername(jwtService, redisUtil, member.getUsername());

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/auth/logout")
                        .contentType(APPLICATION_JSON)
                        .headers(headers)
                        .cookie(cookie)
                )
                .andExpect(status().is2xxSuccessful())
                .andDo(document("logout",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("set access token")),
                        requestCookies(
                                cookieWithName("refreshToken").description("set refresth token"))
                ));
    }

    @Test
    @DisplayName("refresh token 이 없는 경우")
    void no2() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/auth/logout")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("errorMsg")
                        .value(JWT_NOT_FOUND.getErrorMessage()));
    }

    @Test
    @DisplayName("refresh token 이 유효하지 않는 경우")
    void no3() throws Exception {
        Member member = createMember(memberService);
        HttpHeaders headers = getHeaderInAccessToken(jwtService, member);
        Cookie cookie = getCookieByUsername(jwtService, redisUtil, member.getUsername());

        setRefreshTokenToRedis(redisUtil, "test token");

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/auth/logout")
                        .contentType(APPLICATION_JSON)
                        .headers(headers)
                        .cookie(cookie)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("errorMsg")
                        .value(JWT_INVALID.getErrorMessage()));
    }
}