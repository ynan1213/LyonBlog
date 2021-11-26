package com.ynan.bean;

import com.ynan.service.HelloService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuannan
 * @Date 2021/11/26 10:12
 */
//@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "condition3.enabled", havingValue = "true")
public class ConditionImport33 {

	@Bean
	@ConditionalOnMissingBean
	public HelloService getHelloService3() {
		return new HelloService(" ======= condition 33333333333 ==========");
	}
}
