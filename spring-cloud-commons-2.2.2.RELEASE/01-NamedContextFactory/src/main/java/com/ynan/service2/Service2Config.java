package com.ynan.service2;

import com.ynan.client.ClientCommonBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuannan
 * @Date 2021/11/21 22:26
 */
@Configuration(proxyBeanMethods = false)
public class Service2Config {

	@Bean
	Service2Bean service2Bean(ClientCommonBean clientCommonBean) {
		return new Service2Bean(clientCommonBean);
	}

}
