package com.epichust.controller;

import com.epichust.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

	@Autowired
	private UserService userService2;

	public void test() {
		userService2.print("service.print ==================");
	}

}
