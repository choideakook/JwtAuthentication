package com.atowz.global.feign.argumentResolver.refreshToken;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RefreshTokenAuthorization {

    boolean required() default true;
}
