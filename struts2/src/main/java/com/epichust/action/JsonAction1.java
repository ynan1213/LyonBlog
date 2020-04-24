package com.epichust.action;

import com.epichust.entity.User;

public class JsonAction1
{
	private User user;

	public String getUserInfo()
	{
		user = new User();
		user.setAge(18);
		user.setName("张三");
		return "success";
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

}
