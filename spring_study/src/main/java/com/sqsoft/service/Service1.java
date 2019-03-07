package com.sqsoft.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unused")
public class Service1 implements ApplicationContextAware,BeanFactoryAware{
	
	private ApplicationContext applicationContext;
	
	private BeanFactory beanFactory;
	
	@Autowired
	private Service2 s2;
	
	@Autowired(required=false)
	private Service2 s22;
	
	@Value("${str}")
	private String str;
	
	public Service2 getS2() {
		return s2;
	}

	public Service2 getS22() {
		return s22;
	}

	@PostConstruct
	public void p1() {
		
	}
	@PostConstruct
	private String p11() {
		return "a";
	}
	
	@PreDestroy
	private void p2() {
		
	}
	@PreDestroy
	private String p22() {
		return null;
	}
	
	@Override
	public String toString() {
		return "Service1 [str=" + str + "]";
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
