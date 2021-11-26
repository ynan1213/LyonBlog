package com.ynan.client;

import com.ynan.base.BaseBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @Author yuannan
 * @Date 2021/11/21 22:21
 */
@Configuration(proxyBeanMethods = false)
public class ClientCommonConfig {

	private static final String PROPERTY_NAME = "test.context.name";

	@Bean
	ClientCommonBean clientCommonBean(Environment environment, BaseBean baseBean) {
		//在创建 NamedContextFactory 里面的子 ApplicationContext 的时候，会指定 name，这个 name 对应的属性 key 即 PROPERTY_NAME
		return new ClientCommonBean(environment.getProperty(PROPERTY_NAME), baseBean);
	}
}
