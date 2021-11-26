package com.ynan;

import com.ynan.config.XxxDefaultConfiguration;
import com.ynan.feign.RemoteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author yuannan
 * @Date 2021/11/21 09:30
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(defaultConfiguration = XxxDefaultConfiguration.class)
public class StartMain {

	public static void main(String[] args) {
		SpringApplication.run(StartMain.class, args);
	}
}
