package com.ynan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author yuannan
 * @Date 2021/11/21 09:39
 */
@SpringBootApplication
@EnableEurekaClient
@RestController
public class RemoteStartMain {

	public static void main(String[] args) {
		SpringApplication.run(RemoteStartMain.class, args);
	}

	@Value("${spring.application.name}")
	private String applicationName;

	@RequestMapping("/remote/name")
	public User remote(
		@RequestHeader("Accept") String headers,
		@RequestHeader("xxx") String xxx,
		@RequestParam("yuan") String yuan,
		@RequestBody User u) {
		System.out.println("======= headers: " + headers);
		System.out.println("======= xxx: " + xxx);
		System.out.println("======= yuan: " + yuan);
		System.out.println("======= u: " + u);
		return new User("remote", 11, applicationName + " : remote");
	}

}
