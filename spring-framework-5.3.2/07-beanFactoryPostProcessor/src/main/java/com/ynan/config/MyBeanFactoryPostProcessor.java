package com.ynan.config;

import com.ynan.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * @program: spring
 * @description:
 * @author: yn
 * @create: 2021-06-22 17:59
 */

@Component
public class MyBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered
{

	@Autowired
	private UserService userService;


	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
	{
		System.out.println("=== MyBeanFactoryPostProcessor : " + beanFactory.getBeanDefinitionCount());
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException
	{
		System.out.println("2222222222222222222222");
	}

	@Override
	public int getOrder()
	{
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
