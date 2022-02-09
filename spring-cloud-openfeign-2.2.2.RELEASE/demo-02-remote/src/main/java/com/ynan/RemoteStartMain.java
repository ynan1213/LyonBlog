package com.ynan;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public User remote(@RequestParam String name, @RequestHeader("Accept") String headers) {
//		try {
//			System.out.println(
//				"============================================== start : " + Thread.currentThread().getName() + "  time: " + new Date()
//					.toString());
//			TimeUnit.SECONDS.sleep(10);
//			System.out.println(
//				"============================================== end : " + Thread.currentThread().getName() + "  time: " + new Date()
//					.toString());
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println(headers);
		return new User(name, 11, applicationName + " : " + name);
	}

	@RequestMapping("/remote/s")
	public String remote(@RequestParam("name") String name) {
		return "hello world :" + name;
	}

}
