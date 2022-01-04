package com.ynan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/1/4 15:27
 */
@SpringBootApplication
@RestController
public class RemoteServerMain {

	@Value("${spring.application.name}")
	private String applicationName;

	public static void main(String[] args) {
		SpringApplication.run(RemoteServerMain.class, args);
	}

	@RequestMapping("/remote")
	public String remote() {
		return "remote server name : " + applicationName;
	}

}
