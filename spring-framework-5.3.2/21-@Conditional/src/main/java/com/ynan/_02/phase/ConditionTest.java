package com.ynan._02.phase;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author yuannan
 * @date 2022/11/16 10:04
 */
public class ConditionTest implements ConfigurationCondition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String[] beanNamesForType = context.getBeanFactory().getBeanNamesForType(ServiceTestB.class);
		return beanNamesForType.length > 0;
	}

	@Override
	public ConfigurationPhase getConfigurationPhase() {
		return ConfigurationPhase.REGISTER_BEAN;
	}
}
