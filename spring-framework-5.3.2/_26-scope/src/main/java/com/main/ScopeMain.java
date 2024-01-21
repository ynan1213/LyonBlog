package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class ScopeMain {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ScopeMain.class, args);
	}
}
