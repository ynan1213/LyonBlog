package com.epichust.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epichust.dao.StudentDao;
import com.epichust.entity.Student;
import com.epichust.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService
{
	@Autowired
	private StudentDao studentDao;

	@Override
	@Transactional(readOnly = true)
	public List<Student> getAll()
	{
		return studentDao.getAll();
	}

}
