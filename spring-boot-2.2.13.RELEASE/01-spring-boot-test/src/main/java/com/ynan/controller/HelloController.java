package com.ynan.controller;

import com.ynan.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2021/11/17 20:14
 */
@RestController
public class HelloController {

	@Autowired
	private HelloService helloService;

	@Autowired
	private Environment environment;

	@RequestMapping("/hello/{code}")
	public String hello(@PathVariable("code")String code) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader classLoader1 = this.getClass().getClassLoader();
//		return helloService.say();
		return environment.getProperty(code);
	}

}
