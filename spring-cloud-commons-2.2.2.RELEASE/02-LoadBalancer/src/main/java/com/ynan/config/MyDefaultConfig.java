package com.ynan.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author yuannan
 * @Date 2022/1/4 15:43
 */
@Configuration
//@LoadBalancerClient(name = "PROVIDER-00", configuration = XxxConfig.class)
public class MyDefaultConfig {

	/**
	 * 容器默认是不会注入RestTemplate，需要自定义创建，
	 * 带上 @LoadBalanced 注解就表明
	 */
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
}
