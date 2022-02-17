package com.ynan;

import com.ynan.config.XxxDefaultConfiguration;
import com.ynan.entity.User;
import com.ynan.feign.RemoteService;
import com.ynan.feign.RemoteServiceXxx;
import com.ynan.service.HelloService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private HelloService service;

	@Autowired
	private RemoteService remoteService;

//	@Autowired
//	private RemoteServiceXxx remoteServiceXxx;

	public static void main(String[] args) {
		SpringApplication.run(StartMain.class, args);
	}

	@RequestMapping("/xxx")
	public String xxx() {
		return remoteService.remote("world", new User("zhangsan", 23, ""), "上海111").toString();
	}

//	@RequestMapping("/yyy")
//	public String yyy() {
//		return remoteServiceXxx.remote("yyy");
//	}
}
