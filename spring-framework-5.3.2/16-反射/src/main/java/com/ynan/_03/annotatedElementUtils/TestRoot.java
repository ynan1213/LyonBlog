package com.ynan._03.annotatedElementUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * @Author yuannan
 * @Date 2021/10/24 18:15
 */
@TestA
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TestRoot {

}

@TestB
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@interface TestA {

	@AliasFor(value = "c1", annotation = TestC.class)
	String bb() default "testA";
}

@TestC
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@interface TestB {

	@AliasFor(value = "c2", annotation = TestC.class)
	String cc() default "testB";
}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@interface TestC {

	@AliasFor(value = "c2")
	String c1() default "testC";

	@AliasFor(value = "c1")
	String c2() default "testC";
}