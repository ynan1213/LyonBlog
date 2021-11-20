package com.ynan._08.注解合成;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * @Author yuannan
 * @Date 2021/11/17 10:58
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAnnotation {

	@AliasFor("attribute")
	String value() default "";

	@AliasFor("value")
	String attribute() default "";

	String condition() default "";
}
