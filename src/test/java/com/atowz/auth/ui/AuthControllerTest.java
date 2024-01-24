package com.atowz.auth.ui;

import com.atowz.auth.infrastructure.jwt.JwtService;
import com.atowz.auth.infrastructure.redis.RedisUtil;
import com.atowz.global.feign.dto.UserResponse;
import com.atowz.member.application.MemberService;
import com.atowz.member.doamin.entity.Member;
import jakarta.servlet.http.Cookie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.atowz.global.exception.ui.ErrorStatus.JWT_INVALID;
import static com.atowz.global.exception.ui.ErrorStatus.JWT_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.cookies.CookieDocumentation.responseCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("통합: 인증 API")
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class AuthControllerTest extends KakaoClientMock{

    @Autowired
    MockMvc mvc;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JwtService jwtService;
    @Autowired
    MemberService memberService;

    @Value("${jwt.refresh-token-expired-in}")
    private long REFRESH_TOKEN_EXPIRED_IN;

    @BeforeEach
    void setup() {
        requestUserMocking();
        requestTokenMocking();
    }

    @DisplayName("kakao api 인증")
    @Nested
    class kakaoLogin {

        @DisplayName("회원가입 성공")
        @Test
        void no1() throws Exception {
            mvc.perform(MockMvcRequestBuilders
                            .get("/api/auth/kakao")
                            .contentType(APPLICATION_JSON)
                            .param("code", "auth Code")
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(cookie().exists("refreshToken"))
                    .andExpect(header().exists("accessToken"));

            String refreshToken = redisUtil.getValue("1234");
            assertThat(refreshToken).isNotEmpty();
        }

        @DisplayName("로그인 성공")
        @Test
        void no2() throws Exception {
            setRefreshTokenToRedis(redisUtil, "old refresh token");

            mvc.perform(MockMvcRequestBuilders
                            .get("/api/auth/kakao")
                            .contentType(APPLICATION_JSON)
                            .param("code", "auth Code")
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(cookie().exists("refreshToken"))
                    .andExpect(header().exists("accessToken"));

            String refreshToken = redisUtil.getValue("1234");
            assertThat(refreshToken).isNotEqualTo("old refresh token");
        }
    }

    @DisplayName("토큰 재발급")
    @Nested
    class reissueToken {

        @DisplayName("성공")
        @Test
        void no1() throws Exception {
            kakaoLogin(mvc);

            String oldRefreshToken = redisUtil.getValue("1234");
            Thread.sleep(1000L);

            mvc.perform(MockMvcRequestBuilders
                            .get("/api/auth/reissue-token")
                            .contentType(APPLICATION_JSON)
                            .cookie(new Cookie("refreshToken", oldRefreshToken))
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(cookie().exists("refreshToken"))
                    .andExpect(header().exists("accessToken"))
                    .andDo(document("reissue_token",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestCookies(
                                    cookieWithName("refreshToken").description("set refresh token")),
                            responseCookies(
                                    cookieWithName("refreshToken").description("new refresh token")),
                            responseHeaders(
                                    headerWithName("accessToken").description("new access token"))
                    ));

            String newRefreshToken = redisUtil.getValue("1234");
            assertThat(newRefreshToken).isNotEqualTo(oldRefreshToken);
        }

        @DisplayName("token 만료")
        @Test
        void no2() throws Exception {
            kakaoLogin(mvc);

            String refreshToken = redisUtil.getValue("1234");
            redisUtil.deleteData("1234");

            mvc.perform(MockMvcRequestBuilders
                            .get("/api/auth/reissue-token")
                            .contentType(APPLICATION_JSON)
                            .cookie(new Cookie("refreshToken", refreshToken))
                    )
                    .andExpect(status().is4xxClientError())
                    .andExpect(jsonPath("errorMsg")
                            .value(JWT_INVALID.getErrorMessage()));
        }
    }

    @DisplayName("logout")
    @Nested
    class logout {

        @DisplayName("성공")
        @Test
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
                                    cookieWithName("refreshToken").description("set refresh token"))
                    ));
        }

        @DisplayName("token 없음")
        @Test
        void no2() throws Exception {
            mvc.perform(MockMvcRequestBuilders
                            .get("/api/auth/logout")
                            .contentType(APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
                    .andExpect(jsonPath("errorMsg")
                            .value(JWT_NOT_FOUND.getErrorMessage()));
        }

        @DisplayName("token 인증 실패")
        @Test
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

    private Member createMember(MemberService memberService) {
        return memberService.createMember(
                new UserResponse(
                        "1234",
                        "user1",
                        "img"));
    }

    private HttpHeaders getHeaderInAccessToken(JwtService jwtService, Member member) {
        HttpHeaders headers = jwtService.createTokenInHeader(member);
        headers.add("Authorization", headers.getFirst("accessToken"));
        return headers;
    }

    private Cookie getCookieByUsername(JwtService jwtService, RedisUtil redisUtil, String username) {
        String rtk = jwtService.createRefreshToken(username);
        redisUtil.setData(username, rtk, REFRESH_TOKEN_EXPIRED_IN);
        return new Cookie("refreshToken", rtk);
    }

    private void setRefreshTokenToRedis(RedisUtil redisUtil, String testToken) {
        redisUtil.setData("1234", testToken, REFRESH_TOKEN_EXPIRED_IN);
    }
}