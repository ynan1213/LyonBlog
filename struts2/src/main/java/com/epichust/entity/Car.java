package com.epichust.entity;

import java.util.Date;

public class Car
{
	private int id;
	private String name;
	private Date ctime;
	public Car()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public Car(int id, String name, Date ctime)
	{
		super();
		this.id = id;
		this.name = name;
		this.ctime = ctime;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Date getCtime()
	{
		return ctime;
	}
	public void setCtime(Date ctime)
	{
		this.ctime = ctime;
	}
	
	
}
