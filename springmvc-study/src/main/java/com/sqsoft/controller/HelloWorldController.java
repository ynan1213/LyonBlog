package com.sqsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sqsoft.entity.User;

@Controller
@SessionAttributes("user")
public class HelloWorldController {
	
//	@InitBinder
//	public void initBinder() {
//		System.out.println("controller 类中的 initBinder() 方法");
//	}
	
	@ModelAttribute
	public User modelAttribute() {
		User user = new User();
		user.setUsername("xxxxxxxxx");
		user.setPassword("ssss");
		return user;
	}
	
	@RequestMapping("/hello")
	public String hello(Model model) {
		return "hello";
	}
	
	@RequestMapping("/hello1")
	public String hello1(RedirectAttributes ra) {
		ra.addFlashAttribute("ABC", "Hello---World!");
		return "redirect:hello";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public User login() {
		User user = new User();
		user.setUsername("xxxxxxxxx");
		user.setPassword("ssss");
		return user;
	}
	

}
