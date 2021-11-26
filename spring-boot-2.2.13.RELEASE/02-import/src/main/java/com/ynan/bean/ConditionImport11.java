package com.ynan.bean;

import com.ynan.service.HelloService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuannan
 * @Date 2021/11/26 10:09
 */
//@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "condition1.enabled", havingValue = "true")
public class ConditionImport11 {

	@Bean
	@ConditionalOnMissingBean
	public HelloService getHelloService1() {
		return new HelloService(" ======= condition 1111111111 ==========");
	}

}
