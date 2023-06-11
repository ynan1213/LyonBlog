package com.ynan.controller;

import com.ynan.namedContextFactory.TestClient;
import com.ynan.namedContextFactory.TestConfig;
import com.ynan.namedContextFactory.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuannan
 */
@RestController
public class HelloController implements ApplicationContextAware {

	private ApplicationContext context;

	@Autowired
	private TestClient testClient;

	@RequestMapping("/hello")
	public String hello() {

		User user = testClient.getInstance("xxx", User.class);

		return "hello world!!!";
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
