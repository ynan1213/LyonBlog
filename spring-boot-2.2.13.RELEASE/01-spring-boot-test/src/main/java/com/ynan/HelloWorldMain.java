package com.ynan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Author yuannan
 * @Date 2021/11/17 20:13
 */
@SpringBootApplication
public class HelloWorldMain {

	public static void main(String[] args) {
		// apollo.meta=http://config-meta.test.corp.100.me
		System.setProperty("apollo.meta", "http://config-meta.test.corp.100.me");
		System.setProperty("app.id", "ddgy-wms-service");
		System.setProperty("env", "TEST");
		System.setProperty("apollo.cluster", "TE");
		//System.setProperty("apollo.bootstrap.namespaces", "TE");


		ConfigurableApplicationContext applicationContext = SpringApplication.run(HelloWorldMain.class, args);
		ConfigurableEnvironment environment = applicationContext.getEnvironment();

	}
}
