package com.epichust.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private String teacherId;
//	public String getTid()
//	{
//		return "402881fd6d1f94e1016d1f94e2e60001";
//	}
//
//	public void setTid(String id)
//	{
//		
//	}

	public Student()
	{
		super();
	}
	public Student(String id)
	{
		
		setId(id);
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

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TID",insertable=false,updatable=false)
	// @NotFound(action=NotFoundAction.IGNORE)
	// @Where(clause = "TID != ''")
	public Teacher getTeacher()
	{
		return teacher;
	}

	public void setTeacher(Teacher teacher)
	{
		this.teacher = teacher;
	}
	
	@Column(name = "TID")
	public String getTeacherId()
	{
		return teacherId;
	}
	public void setTeacherId(String teacherId)
	{
		this.teacherId = teacherId;
	}
	
	@Override
	public String toString()
	{
		return "Student [name=" + name + ", age=" + age + ", teacher=" + teacher + ", id=" + getId() +"]";
	}

}
