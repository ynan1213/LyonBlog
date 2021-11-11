package com.epichust.controller;

import com.epichust.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@RequestMapping(value = "/login")
	@ResponseBody
	public User login() {
		return new User("11", 22);
	}
}