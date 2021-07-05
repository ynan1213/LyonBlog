package com.ynan.entity;

import org.springframework.stereotype.Component;

/**
 * @program: spring
 * @description:
 * @author: yn
 * @create: 2021-07-01 23:05
 */

// @Component
public class Father
{
	private String name;

	public void print()
	{
		System.out.println("father : " + name);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
