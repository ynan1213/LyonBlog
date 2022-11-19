package com.ynan._01.javaAnno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author yuannan
 * @Date 2021/10/24 10:35
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {

	String[] value() default {};

	String params() default "";
}
