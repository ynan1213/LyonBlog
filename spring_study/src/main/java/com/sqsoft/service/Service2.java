package com.sqsoft.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;

@Controller
@SuppressWarnings("unused")
public class Service2 implements ApplicationContextAware,BeanFactoryAware{

	private String field;

//----------------------------------------------------------
	public void setS1() {

	}
	public String getS1() {
		return "a";
	}
//----------------------------------------------------------
	public void setS11(String s) {

	}
//----------------------------------------------------------	
	public String getS2() {
		return null;
	}
//----------------------------------------------------------	
	public void getS22() {
		
	}
@Override
public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
	// TODO Auto-generated method stub
	
}
@Override
public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	// TODO Auto-generated method stub
	
}
}
