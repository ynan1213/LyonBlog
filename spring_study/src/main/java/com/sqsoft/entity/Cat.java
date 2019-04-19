package com.sqsoft.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Cat implements Animal{
	
	@Value("${catName}")
	private String name;

	@Override
	public void call() {
		System.out.println("cat: "+name+"喵喵喵");
		
	}
	
}
