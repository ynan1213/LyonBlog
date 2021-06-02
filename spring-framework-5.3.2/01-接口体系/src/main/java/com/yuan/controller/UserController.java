package com.epichust.controller;

import com.epichust.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class UserController
{
	@Autowired
	@Qualifier("userService1")
	private UserService service;

//	@Autowired
//	private BeanService service1;

	public void test()
	{
		service.print("service.print ==================");
		//service1.print();
	}


}
