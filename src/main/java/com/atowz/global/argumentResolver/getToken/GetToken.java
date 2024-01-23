package com.atowz.global.argumentResolver.getToken;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetToken {

    boolean required() default true;
}
