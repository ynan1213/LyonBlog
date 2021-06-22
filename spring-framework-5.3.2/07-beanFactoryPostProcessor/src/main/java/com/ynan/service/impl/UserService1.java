package com.ynan.service.impl;

import com.ynan.service.UserService;
import org.springframework.stereotype.Service;

//@Component
@Service
public class UserService1 implements UserService
{
	@Override
	public void print(String msg)
	{
		System.out.println("UserService 11111111111111111111111   " + msg);
	}
}
