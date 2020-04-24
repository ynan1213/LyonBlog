package com.epichust.entity;

public class Student
{
	private String name;
	private Integer age;
	private int num;
	
	private Teacher teacher;
	
	public Student()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(String name)
	{
		super();
		this.name = name;
	}

	public Student(Integer age)
	{
		super();
		this.age = age;
	}

	public Student(String name, Integer age, int num)
	{
		super();
		this.name = name;
		this.age = age;
		this.num = num;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getAge()
	{
		return age;
	}

	public void setAge(Integer age)
	{
		this.age = age;
	}

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	public Teacher getTeacher()
	{
		return teacher;
	}

	public void setTeacher(Teacher teacher)
	{
		this.teacher = teacher;
	}

	@Override
	public String toString()
	{
		return "Student [name=" + name + ", age=" + age + ", num=" + num + ", teacher=" + teacher + "]";
	}
	
}
