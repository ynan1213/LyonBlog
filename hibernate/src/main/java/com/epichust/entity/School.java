package com.epichust.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="SCHOOL")
public class School extends BaseEntity
{
	private String name;
	private String address;
	
	public School()
	{
		super();
	}
	
	public School(String name, String address)
	{
		super();
		this.name = name;
		this.address = address;
	}
	
	@Column(name="NAME")
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	@Column(name="ADDRESS")
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}

	@Override
	public String toString()
	{
		return "School [name=" + name + ", address=" + address + "]";
	}
}
