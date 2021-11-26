package com.ynan.service1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuannan
 * @Date 2021/11/21 22:24
 */
@Configuration(proxyBeanMethods = false)
public class Service1Config2 {

	@Bean
	Service1Bean2 service1Bean2() {
		return new Service1Bean2();
	}
}
