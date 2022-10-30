package com.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/7/3 12:23
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class Provider02Main {

	@Value("${spring.application.name}")
	private String applicationName;

	public static void main(String[] args) {
		SpringApplication.run(Provider02Main.class, args);
	}

	@RequestMapping("/hello")
	public String hello() {
		return "hello 02" + applicationName + " !!!";
	}
}
