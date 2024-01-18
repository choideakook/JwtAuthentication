package com.atowz.global.argumentResolver.accessTokenToMember;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessTokenToMember {

    boolean required() default true;
}
