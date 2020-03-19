package com.epichust.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloAction
{
	@RequestMapping("/index")
	public String toIndex()
	{
		return "index";
	}
}
