package com.ynan.service1;

/**
 * @Author yuannan
 * @Date 2021/11/21 22:24
 */

import com.ynan.client.ClientCommonBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class Service1Config1 {

	@Bean
	Service1Bean1 service1Bean1(ClientCommonBean clientCommonBean) {
		return new Service1Bean1(clientCommonBean);
	}

}
