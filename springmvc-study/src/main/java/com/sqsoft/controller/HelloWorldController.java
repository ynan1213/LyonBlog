package com.sqsoft.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sqsoft.entity.User;

@Controller
@SessionAttributes("user")
public class HelloWorldController {
	
	@InitBinder
	public void initBinder() {
		System.out.println("controller 类中的 initBinder() 方法");
	}
	
	@ModelAttribute
	public User modelAttribute() {
		User user = new User();
		user.setUsername("xxxxxxxxx");
		user.setPassword("ssss");
		return user;
	}
	
	@RequestMapping("/hello")
	public String handleRequest(Model model) {
		model.addAttribute("ABC", "Hello---World!");
		return "hello";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/submit")
	public String submit(User user,Model model) {
		System.out.println(user);
		model.addAttribute("username", user.getUsername());
		return "success";
	}
	
	public String xxxx() {
		return "success";
	}

}
