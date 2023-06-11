package com.ynan;

import com.ynan.namedContextFactory.TestClient;
import com.ynan.namedContextFactory.TestConfig;
import com.ynan.namedContextFactory.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yuannan
 */
@SpringBootApplication
@ComponentScan("com.ynan.controller")
public class ParentContextMain {

	public static void main(String[] args) {
		SpringApplication.run(ParentContextMain.class, args);
	}

	@Bean
	public TestClient testClient() {
		return new TestClient(TestConfig.class);
	}

	@Bean
	public User	getUser() {
		return new User();
	}
}
