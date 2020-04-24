package com.epichust.action;

import org.springframework.stereotype.Controller;

@Controller
public class DynamicAction
{	
	public String update()
	{
		return "update";
	}
	
	public String add()
	{
		return "add";
	}
	
	public String delete()
	{
		return "delete";
	}
	
}
