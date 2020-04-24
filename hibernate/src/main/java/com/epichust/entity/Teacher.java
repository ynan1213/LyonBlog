package com.epichust.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TEACHER")
public class Teacher extends BaseEntity
{
	private String name;
	private String age;
	
	private School school;
	private String schoolId;
	
	private Set<Student> students;
	
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

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="SCHOOL_ID",insertable=false,updatable=false)
	public School getSchool()
	{
		return school;
	}

	public void setSchool(School school)
	{
		this.school = school;
	}
	
	@Column(name="SCHOOL_ID")
	public String getSchoolId()
	{
		return schoolId;
	}

	public void setSchoolId(String schoolId)
	{
		this.schoolId = schoolId;
	}

	
	@OneToMany(mappedBy = "teacherId")
	public Set<Student> getStudents()
	{
		return students;
	}

	public void setStudents(Set<Student> students)
	{
		this.students = students;
	}

	@Override
	public String toString()
	{
		return "Teacher [name=" + name + ", age=" + age + ", school=" + school + "]";
	}
	
}
