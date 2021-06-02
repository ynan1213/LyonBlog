package com.sqsoft.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String toLogin(Model mode) {
		mode.addAttribute("aaa", "张三");
		return "login";
	}
	
	@GetMapping("/registe")
	public String toRegiste() {
		return "registe";
	}
	
	@PostMapping("/login")
	public String login(String username1, String password1) {
		
		UsernamePasswordToken token = new UsernamePasswordToken(username1,password1,true);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			Session session = subject.getSession();
			session.setAttribute("user", subject.getPrincipal());
			return "index";
		}catch(UnknownAccountException e) {
			throw e;
		}catch(IncorrectCredentialsException e) {
			throw new IncorrectCredentialsException("密码不正确");
		}
	}
	
	@PostMapping("/registe")
	public String registe(String username, String password) {
		System.out.println("aaa");
		return null;
	}
	
	@RequestMapping("/yuan")
	public String yuan() {
		return "yuan";
	}
	
	
	@RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
//        session.removeAttribute("user");
        return "login";
    }
}
