package com.ynan.namedContextFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author yuannan
 */
@Configuration(proxyBeanMethods = false)
public class TestConfig implements EnvironmentAware {

	private Environment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public String say(String prop) {
		String property = environment.getProperty(prop);
		return property;
	}

	@Bean
//	@ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
	public User user() {
		return new User();
	}

}
