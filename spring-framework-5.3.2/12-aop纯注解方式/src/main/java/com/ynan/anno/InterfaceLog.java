package com.ynan.anno;

import java.lang.annotation.*;

/**
 * @author yuannan
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InterfaceLog {

	String code() default "";

	String name() default "";
}
