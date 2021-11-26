package com.ynan.config;

import feign.Logger;
import feign.Logger.Level;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @Author yuannan
 * @Date 2021/11/22 19:26
 */
//@Configuration(proxyBeanMethods = false)
public class XxxDefaultConfiguration {

	public XxxDefaultConfiguration(ApplicationContext context) {
		System.out.println("全局配置 XxxDefaultConfiguration 初始化中 ..... 会被每一个client子容器初始化，当前容器：" + context.getDisplayName());
	}

}
