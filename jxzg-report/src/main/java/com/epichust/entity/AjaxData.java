package com.epichust.entity;

public class AjaxData
{
	private String state;
	private String message;
	
	public final static String SUCCESS = "success";
	public final static String FAIL = "fail";
	
//	public static AjaxData SUCCESS = new AjaxData("success","验证成功");
//	public static AjaxData ERROR = new AjaxData("fail","用户名或密码不正确");

	public AjaxData()
	{
		super();
	}

	public AjaxData(String state)
	{
		super();
		this.state = state;
	}

	public AjaxData(String state, String message)
	{
		super();
		this.state = state;
		this.message = message;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
	
}
