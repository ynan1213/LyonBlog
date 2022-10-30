package com.ynan;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/7/3 12:31
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient(autoRegister = false)
public class Consumer01Main {

	@Value("${spring.application.name}")
	private String applicationName;

	@Resource
	private DiscoveryClient discoveryClient;

	public static void main(String[] args) {
		SpringApplication.run(Consumer01Main.class, args);
	}

	@RequestMapping("/hello")
	public String hello() {
		return "hello " + applicationName + " !!!";
	}

	@RequestMapping("/instance")
	public String instance() {
		return discoveryClient.getServices().toString();
	}
}
