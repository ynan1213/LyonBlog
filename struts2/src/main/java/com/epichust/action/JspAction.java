package com.epichust.action;

public class JspAction
{
	private String username;
	private int password;
	
	public String fun()
	{
		System.out.println(username + password);
		return "world";
	}


	public String getUsername()
	{
		return username;
	}


	public void setUsername(String username)
	{
		this.username = username;
	}


	public int getPassword()
	{
		return password;
	}


	public void setPassword(int password)
	{
		this.password = password;
	}
	
}
