package com.epichust.example4.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Service2 {
	
	@Bean
	public String xxx() {
		return "aaa";
	}
	
}
