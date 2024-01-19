package com.atowz.global.exception.ui;

import lombok.Getter;

@Getter
public enum ErrorStatus {

    //-- about jwt --//
    JWT_INVALID("유효하지 않은 토큰", 401),
    JWT_EXPIRED("만료된 토큰", 401),
    JWT_NOT_FOUND("토큰을 찾을 수 없음", 400),
    JSON_DOSE_NOT_SUPPORT("json String 으로 변환 실패", 400);

    private final String errorMessage;
    private final int status;

    ErrorStatus(String errorMessage, int status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }
}
