package com.ynan.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuannan
 * @Date 2021/11/21 22:20
 */
@Configuration(proxyBeanMethods = false)
public class BaseConfig {

	@Bean
	BaseBean baseBean() {
		return new BaseBean();
	}
}
