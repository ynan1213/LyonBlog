package com.ynan.config;

import org.springframework.context.annotation.Bean;

/**
 * @Author yuannan
 * @Date 2021/11/3 10:45
 */
// @Configuration注解可以不要，因为内部有了@Bean
//@Configuration
public class RootConfig {

	@Bean
	public Service getService() {
		return new Service();
	}

}
