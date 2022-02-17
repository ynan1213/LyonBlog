package com.ynan.config;

import feign.Feign;
import feign.Logger;
import feign.Logger.Level;
import feign.Retryer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @Author yuannan
 * @Date 2021/11/22 19:27
 */
//@Configuration(proxyBeanMethods = false)
public class XxxCustomConfiguration  {

	public XxxCustomConfiguration(ApplicationContext context) {
		System.out.println("指定client配置 XxxDefaultConfiguration 初始化中 ..... 会被每一个client子容器初始化，当前容器：" + context.getDisplayName());
	}

	@Bean("xx")
	@Scope("prototype")
//	@ConditionalOnMissingBean
	public Feign.Builder feignBuilder(Retryer retryer) {
		return Feign.builder().retryer(retryer);
	}

}
