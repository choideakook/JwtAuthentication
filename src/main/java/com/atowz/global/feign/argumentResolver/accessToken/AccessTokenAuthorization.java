package com.atowz.global.feign.argumentResolver.accessToken;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessTokenAuthorization {

    boolean required() default true;
}
