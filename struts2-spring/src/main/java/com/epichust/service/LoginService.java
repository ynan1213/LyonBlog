package com.epichust.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService
{
	public boolean login(String name)
	{
		System.out.println("aaaaaaa");
		return "yuan".equals(name);
	}
}
