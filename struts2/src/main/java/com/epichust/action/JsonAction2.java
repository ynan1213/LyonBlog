package com.epichust.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.epichust.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonAction2
{
	public String getUserInfo() throws IOException
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		List<User> list = new ArrayList<User>();
		list.add(new User("张三",23));
		list.add(new User("李四",24));
		list.add(new User("王五",25));
		
		ObjectMapper mapper = new ObjectMapper();
		writer.write(mapper.writeValueAsString(list));
		writer.flush();
		return null;
	}
}
