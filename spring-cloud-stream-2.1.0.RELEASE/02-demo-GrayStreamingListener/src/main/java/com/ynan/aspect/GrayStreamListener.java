package com.ynan.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.annotation.AliasFor;

/**
 * @Author yuannan
 * @Date 2021/11/22 16:05
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@StreamListener
@Documented
public @interface GrayStreamListener {

    /**
     * 灰度方案
     */
    int grayType() default 0;

    /**
     * 灰度表达式
     */
    String grayCondition() default "";

    @AliasFor(annotation = StreamListener.class)
    String value() default "";

    @AliasFor(annotation = StreamListener.class)
    String target() default "";

    @AliasFor(annotation = StreamListener.class)
    String condition() default "";

    @AliasFor(annotation = StreamListener.class)
    String copyHeaders() default "true";
}
