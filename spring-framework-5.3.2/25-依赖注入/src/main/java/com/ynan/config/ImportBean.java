package com.ynan.config;

import com.ynan.service.OneServiceImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author yuannan
 */
public class ImportBean implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
			BeanDefinitionRegistry registry) {

		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(OneServiceImpl.class)
				.getBeanDefinition();
//		beanDefinition.getPropertyValues().add("xxx", "xxxxxxx");

		beanDefinition.getPropertyValues().add("twoServiceImpl", new RuntimeBeanReference("twoServiceImpl"));

		//beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

		registry.registerBeanDefinition("one", beanDefinition);

	}
}
