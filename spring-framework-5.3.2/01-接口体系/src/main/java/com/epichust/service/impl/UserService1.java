package com.epichust.service.impl;

import com.epichust.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService1 implements UserService
{
	@Override
	public void print(String msg)
	{
		System.out.println("UserService 11111111111111111111111");
	}
}
