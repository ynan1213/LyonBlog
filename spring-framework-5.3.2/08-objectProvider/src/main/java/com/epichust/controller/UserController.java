package com.epichust.controller;

import com.epichust.service.UserService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.Iterator;

@Controller
public class UserController
{
	private UserService service;

	public UserController(ObjectProvider<UserService> objectProvider)
	{
		System.out.println("进入");

		// 如果有多个bean，这里仍会报错
		// UserService ifAvailable = objectProvider.getIfAvailable();

		Iterator<UserService> iterator = objectProvider.iterator();
		while (iterator.hasNext())
		{
			iterator.next().print("aaa");
		}

		System.out.println("==================");
		System.out.println(objectProvider.getIfAvailable());
		System.out.println(objectProvider.getIfUnique());

	}

	public void test()
	{
		service.print("service.print ==================");
	}


}
