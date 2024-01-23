package com.atowz.global.exception.jwt;

import com.atowz.global.exception.ui.ErrorStatus;
import io.jsonwebtoken.JwtException;
import lombok.Getter;

@Getter
public class InvalidJwtException extends JwtException {

    private final int status;

    public InvalidJwtException(ErrorStatus response) {
        super(response.getErrorMessage());
        this.status = response.getStatus();
    }
}
