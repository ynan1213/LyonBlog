package com.ynan;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
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
	public String remote(@RequestParam String name) {
		try {
			System.out.println("============================================== start : " + Thread.currentThread().getName() + "  time: "+ new Date().toString());
			TimeUnit.SECONDS.sleep(12);
			System.out.println("============================================== end : " + Thread.currentThread().getName() + "  time: "+ new Date().toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return applicationName + " : " + name;
	}

}
