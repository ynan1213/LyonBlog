package com.sqsoft.lookup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Component
public class C {
	
	@Bean
	public A a() {
		A a = new A();
		a.setB(b());
		return a;
	}
	
	@Bean
	public B b() {
		return new B();
	}
}
