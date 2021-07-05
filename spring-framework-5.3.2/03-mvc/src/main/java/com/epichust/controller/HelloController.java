package com.epichust.controller;

import com.epichust.entity.User;
import com.epichust.expcetion.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController
{
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/xml")
	@ResponseBody
	public User login1()
	{
		// if(1 == 1)
		// 	throw new UserException();
		return new User("11", 22);
	}
}