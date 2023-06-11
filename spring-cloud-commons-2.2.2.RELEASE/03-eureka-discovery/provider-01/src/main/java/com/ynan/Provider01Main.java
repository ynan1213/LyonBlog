package com.ynan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/7/3 10:57
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class Provider01Main {

	@Value("${spring.application.name}")
	private String applicationName;

	public static void main(String[] args) {
		SpringApplication.run(Provider01Main.class, args);
	}

	@RequestMapping("/hello")
	public String hello() {
//		if (1==1) throw new RuntimeException("XXXXXXXXXX");
		return "hello 01" + applicationName + " !!!";
	}

}
