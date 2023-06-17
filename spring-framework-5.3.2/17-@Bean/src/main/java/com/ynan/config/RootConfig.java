package com.ynan.config;

import con.ynan.service.UserService;
import con.ynan.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @Author yuannan
 * @Date 2021/11/3 10:45
 */
// @Configuration注解可以不要，因为内部有了@Bean
//@Configuration
//@Import(UserServiceImpl.class)
public class RootConfig {

	public RootConfig(){}

//	@Autowired
//	public RootConfig(UserService userService){}
//
//	@Autowired
//	private UserService userService;
//
//	@Lookup
//	public void test() {}

	@Bean
	public UserService getUserService() {
		return new UserServiceImpl();
	}


}
