package com.ynan._02.phase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuannan
 * @date 2022/11/16 19:34
 */
@Configuration
public class ServiceTestB {

	@Bean
	public Service getSB() {
		return new Service();
	}
}
