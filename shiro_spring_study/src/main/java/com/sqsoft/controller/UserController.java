package com.sqsoft.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	
	@GetMapping("/login")
	public String toLoginPage() {
		return "login";
	}
	
	@GetMapping("/errer")
	public String toErrerPage() {
		return "errer";
	}
	
	@PostMapping("/login")
	public String login(String username,String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(username,password,true);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			Session session = subject.getSession();
			session.setAttribute("user", subject.getPrincipal());
			return "success";
		}catch(UnknownAccountException e) {
			throw e;
		}catch(IncorrectCredentialsException e) {
			throw new IncorrectCredentialsException("密码不正确");
		}
	}
	
}
