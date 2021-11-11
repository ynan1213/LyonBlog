package com.ynan.config;

/**
 * @Author yuannan
 * @Date 2021/11/3 10:41
 */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.ynan")
@Import(BeanImportTest.class)
public class RootConfig3 implements SmartInitializingSingleton, BeanFactoryAware {

	private BeanFactory beanFactory;

	@Override
	public void afterSingletonsInstantiated() {
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
