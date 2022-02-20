package com.epichust.controller;

import com.epichust.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

	@Autowired
//	@Qualifier("userService2")
	private UserService userService2;

	@Value("${xxx}")
	private String str;

//	public UserController(){
//
//	}

//	public UserController(@Qualifier("userService2") UserService userService) {
//		this.userService = userService;
//	}

//	public UserController(UserService userService, UserService u2) {
//		this.userService = userService;
//	}

	public void test() {
		userService2.print("service.print ==================");
	}

}
