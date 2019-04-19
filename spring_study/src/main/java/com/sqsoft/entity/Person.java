package com.sqsoft.entity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component()
public class Person {
	
	@Autowired
	@Qualifier("cat")
	private Animal animal;
	
	@Resource(name="dog")
	private Animal an;
	
	public void play() {
		animal.call();
	}
	
	public Person() {
		System.out.println("person 无参构造函数");
	}

	@Autowired
	public Person(@Qualifier("dog")Animal animal,@Value("aaa")String s) {
		System.out.println("person 有参构造函数" + s);
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("person postConstruct-------------------");
	}
	
	@PreDestroy
	public void preDestroy() {
		System.out.println("person preDestroy-------------------");
	}
	
	
}
