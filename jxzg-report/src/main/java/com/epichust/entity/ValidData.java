package com.epichust.entity;

public class ValidData
{
	private String type;
	private String data;
	
	public ValidData()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ValidData(String type, String data)
	{
		super();
		this.type = type;
		this.data = data;
	}


	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getData()
	{
		return data;
	}
	public void setData(String data)
	{
		this.data = data;
	}
	
}
