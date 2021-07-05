package com.epichust.controller;

import com.epichust.service.UserService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;

@Controller
public class UserController
{
	// @Resource(name = "userService1")
	// private UserService userService2;

	@Inject
	@Named("userService2")
	private UserService userService;

	public void test()
	{
		userService.print("service.print ==================");
	}


}
