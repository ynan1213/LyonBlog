package com.epichust.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.epichust.service.LoginService;

@Controller
@Scope("prototype")
public class LoginAction
{
	@Autowired
	private LoginService service;
	
	private String username;
	
	public String login()
	{
		if(service.login(username))
		{
			return "success";
		}else
		{
			return "fail";
		}
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
	
}
