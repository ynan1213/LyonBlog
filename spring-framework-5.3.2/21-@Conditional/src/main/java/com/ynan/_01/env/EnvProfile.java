package com.ynan._01.env;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;

/**
 * @author yuannan
 * @date 2022/11/16 09:29
 */
@Conditional(MyCondition.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnvProfile {

	Env value() default Env.DEV;

	enum Env {
		DEV, TEST, PROD
	}
}
