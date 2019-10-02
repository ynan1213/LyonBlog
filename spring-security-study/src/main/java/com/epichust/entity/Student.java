package com.epichust.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "STUDENT")
@Entity
public class Student extends BaseEntity
{
	private String name;
	private String age;

	private Teacher teacher;

	public Student()
	{
		super();
	}

	public Student(String name, String age)
	{
		super();
		this.name = name;
		this.age = age;
	}

	@Column(name = "name", length = 20)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "age", length = 20)
	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	@ManyToOne
	@JoinColumn(name = "TID")
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
		return "Student [name=" + name + ", age=" + age + ", teacher=" + teacher + "]";
	}

}
