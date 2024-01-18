package com.atowz.global.argumentResolver.refreshTokenToMember;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RefreshTokenToMember {

    boolean required() default true;
}
