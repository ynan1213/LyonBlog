package com.epichust.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.epichust.entity.Student;
import com.epichust.service.StudentService;

@Controller
@Scope("prototype")
public class HelloAction
{
	@Autowired
	private StudentService serivce;
	
	public String hello()
	{
		List<Student> list = serivce.getAll();
		for (Student stu : list)
		{
			System.out.println(stu);
		}
		System.out.println("Hello World!");
		return "success";
	}

}
