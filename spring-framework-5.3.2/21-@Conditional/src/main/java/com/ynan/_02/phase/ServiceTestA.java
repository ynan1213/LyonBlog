package com.ynan._02.phase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuannan
 * @date 2022/11/16 10:46
 */
@Configuration
@Conditional(ConditionTest.class)
public class ServiceTestA {

	@Bean
	public Service getSA() {
		return new Service();
	}

}
