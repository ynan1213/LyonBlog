package com.epichust.controller;

import com.epichust.entity.User;
import com.epichust.expcetion.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HelloController {

	@RequestMapping(value = "/login")
	@ResponseBody
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "xxxxxxxxxxxxyyyyyyyyyyyyy")
	public User login(@RequestBody User user) {
		return new User("11", 22);
	}
}