package com.sqsoft.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {
	
	@RequestMapping(value={"/hello"})
	public String hello() {
		throw new UserException();
	}
	
	@RequestMapping(value={"/login"})
	public String login() {
		return "login";
	}
	
	@RequestMapping(value={"/submit"})
	public String submit(@RequestBody String username) {
		System.out.println(username);
		return "success";
	}
	
}
