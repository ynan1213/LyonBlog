package com.ynan.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author yuannan
 * @Date 2021/11/3 10:42
 */
public class BeanImportTest implements ImportBeanDefinitionRegistrar {

	public BeanImportTest() {
		System.out.println("BeanImportTest 初始化................");
	}

	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		System.out.println("registerBeanDefinitions xxxxxxxxxxxxxxxxx");
	}
}
