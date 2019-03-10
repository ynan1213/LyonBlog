package com.sqsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sqsoft.entity.User;

@Controller
public class HelloWorldController {

	@RequestMapping("/hello")
	public ModelAndView handleRequest() {
		ModelAndView mv = new ModelAndView("hello");
		mv.addObject("ABC", "Hello---World!");
		return mv;
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

}
