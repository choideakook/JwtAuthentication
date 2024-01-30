# Kakao API 를 사용한 JWT 기반 인증서버

## 소프트웨어 내부 아키택처
- `interceptor` 계층에서 JWT 의 유효성 검사를 진행합니다.
- `HandlerMethodArgumentResolver` 계층에서 JWT 에서 회원 객체를 조회해 `Controller` 계층으로 전달합니다.

<img width="818" alt="아키택처" src="https://github.com/choideakook/Jwt_Authentication/assets/115536240/5bb16d5f-62f3-42ad-a4c0-9dd7ecf01e1b">


## 핵심 기능
### 0. 개요
- 보안을 강화하기 위해 refresh token rotation 전략을 채택했습니다.
  - access token 의 만료시간을 짧게 생성합니다.
  - access token 갱신시 refresh token 도 갱신합니다.
  - 하나의 계정당 하나의 refresh token 만 redis 에 저장합니다.

### 1. 회원가입
- Kakao 서버로부터 회원 정보를 조회해 SQL 에 저장합니다.
- 회원 정보를 access token 과 refresh token 으로 암호화해 클라이언트에게 전달합니다.
  - refresh token 은 redis 에 저장합니다.
    - 이 때 key 는 username 으로, value 는 token 으로 합니다.

### 2. 로그인
- Kakao 서버로부터 회원 정보를 조회해 SQL 에서 회원을 조회합니다.
- access token 과 refresh token 을 생성해 반환합니다.
  - refresh token 은 redis 에 저장합니다.
  - 기존에 저장된 token 은 사용할 수 없게 됩니다.

### 3. token 갱신
- refresh token 을복호화해 username 을 조회합니다.
- redis 에서 token 을 조회해 클라이언트의 token 과 비교합니다.
  - 만약 다르다면 401 error 를 반환합니다.
- SQL 에서 회원을 조회해 token 을 갱신해 반환합니다.
  - access token 을 key 값으로 redis 에 저장합니다.
  - refresh token 은 username 을 key 값으로 redis 에 저장합니다.

### 4. access token 만료
- interceptor 계층에서 진행되는 유효성 검사에서 401 error 를 발생시킵니다.
  - JWT 가 만료되거나, redis 에 access token 이 저장된 경우 유효성 검사가 실패하게 됩니다.

### 5. 로그아웃
- token 의 유효성을 검사합니다.
- access token 을 key 값으로 redis 에 저장합니다.
- redis 에 저장된 refresh token 을 삭제합니다.
