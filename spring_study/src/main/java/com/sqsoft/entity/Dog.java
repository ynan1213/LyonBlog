package com.sqsoft.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope("prototype")
@Lazy
@Import(String.class)
public class Dog {
	
	@Autowired
	@Qualifier
	private String name;
	
	public Dog() {
		super();
	}

	public Dog(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Required
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Dog [name=" + name + "]";
	}
	
}
