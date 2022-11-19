package com.ynan.service;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author yuannan
 * @date 2022/11/17 21:26
 */
public class RegistrarImport implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		//定义一个bean：Service1
		BeanDefinition service1BeanDinition = BeanDefinitionBuilder.genericBeanDefinition(SimpleImport.class).getBeanDefinition();
		registry.registerBeanDefinition("service1", service1BeanDinition);

		//定义一个bean：Service2，通过addPropertyReference注入service1
		BeanDefinition service2BeanDinition = BeanDefinitionBuilder.genericBeanDefinition(SimpleImport.class).getBeanDefinition();
		registry.registerBeanDefinition("service2", service2BeanDinition);

		// 可以注册多个
	}
}
