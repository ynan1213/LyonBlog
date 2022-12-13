package com.ynan;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan("com.ynan")
public class MyBeanPostProcessor implements BeanPostProcessor {

	@Autowired
	private UserService2 userService2;

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("com.ynan.MyBeanPostProcessor :" + beanName);
		return null;
	}
}
