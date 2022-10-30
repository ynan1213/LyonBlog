package com.ynan;

import com.ynan.config.XxxDefaultConfiguration;
import com.ynan.entity.User;
import com.ynan.feign.RemoteService;
import javax.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2021/11/21 09:30
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(defaultConfiguration = XxxDefaultConfiguration.class)
@RestController
public class StartMain {

	@Resource
	private RemoteService remoteService;

	public static void main(String[] args) {
		SpringApplication.run(StartMain.class, args);
	}

	@RequestMapping("/xxx")
	public String xxx() {
		try {
			return remoteService.remote(new User(null, 23, "")).toString();
		} catch (Exception e) {
			throw e;
		}
	}

}
