package com.sqsoft.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Dog implements Animal{
	
	@Value("${dogName}")
	private String name;

	@Override
	public void call() {
		System.out.println("dog: "+name+"汪汪汪");
		
	}
	
	
}
