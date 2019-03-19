package com.sqsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value= {"attr1","attr2"})
public class ControllerSessionAttributes {
	
	@RequestMapping("/index1")
	public ModelAndView index1() {
		ModelAndView mav = new ModelAndView("hello");
		mav.addObject("attr1", "sqsoft");
		mav.addObject("attr2", "coso");
		return mav;
	}
	
	@RequestMapping("/index2")
	public ModelAndView index2(@ModelAttribute("attr1")String s1,@ModelAttribute("attr2")String s2) {
		System.out.println("s1="+s1+"----s2="+s2);
		ModelAndView mav = new ModelAndView("success");
		return mav;
		
	}
	
	@RequestMapping("/index3")
	public ModelAndView index3(SessionStatus status) {
		ModelAndView mav = new ModelAndView("success.jsp");
		status.setComplete();
		return mav;
	}
}
