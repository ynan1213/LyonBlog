package com.epichust.controller;

import com.epichust.expcetion.UserException;
import com.epichust.entity.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
//@RequestMapping(value = "/hello", method = RequestMethod.GET)
@SessionAttributes({"xxx", "yyy"})
public class HelloController
{
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(df, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

//	@ModelAttribute
//	public User modelAttribute()
//	{
//		System.out.println("modelAttribute.....HelloController.............................");
//		return new User("张三", 23);
//	}

	@GetMapping(value = "/login")
	//@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	public User login(Integer xxx)
	{
		System.out.println(xxx);
		if(true)
		{
			throw new UserException("XXXX");
		}
		return new User("xxxxxxx", 2222222);
	}

	@PostMapping(value = "/login")
	@ResponseBody
	public User login1(@RequestBody User user)
	{
		System.out.println(user);
		return user;
	}
}