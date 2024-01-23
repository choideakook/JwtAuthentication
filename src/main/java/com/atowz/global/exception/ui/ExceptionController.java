package com.atowz.global.exception.ui;

import com.atowz.global.exception.jwt.InvalidJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ErrorResponse> invalidJwtExceptionHandler(InvalidJwtException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getMessage()));
    }
}
