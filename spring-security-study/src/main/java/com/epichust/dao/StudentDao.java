package com.epichust.dao;

import java.util.List;

import com.epichust.entity.Student;

public interface StudentDao
{
	public List<Student> getAll();
	
	public Student getById(String id);
	
}
