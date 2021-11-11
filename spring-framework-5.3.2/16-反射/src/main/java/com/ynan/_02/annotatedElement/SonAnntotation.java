package com.ynan._02.annotatedElement;

/**
 * @Author yuannan
 * @Date 2021/10/24 11:06
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SonAnntotation {

}
