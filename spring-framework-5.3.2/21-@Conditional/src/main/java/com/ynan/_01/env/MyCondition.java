package com.ynan._01.env;


import com.ynan._01.env.EnvProfile.Env;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author yuannan
 * @date 2022/11/16 09:32
 */
public class MyCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

		EnvProfile.Env value = (EnvProfile.Env) metadata.getAnnotationAttributes(EnvProfile.class.getName())
				.get("value");
		return Env.DEV == value;
	}
}
