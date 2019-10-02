package com.epichust.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TEACHER")
public class Teacher extends BaseEntity
{
	private String name;
	private String age;
	
	private School school;
	
	public Teacher()
	{
		super();
	}

	public Teacher(String name, String age)
	{
		super();
		this.name = name;
		this.age = age;
	}

	@Column(name="name")
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	@Column(name="age")
	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	@ManyToOne
	@JoinColumn(name="SCHOOL_ID")
	public School getSchool()
	{
		return school;
	}

	public void setSchool(School school)
	{
		this.school = school;
	}

	@Override
	public String toString()
	{
		return "Teacher [name=" + name + ", age=" + age + ", school=" + school + "]";
	}
	
}
