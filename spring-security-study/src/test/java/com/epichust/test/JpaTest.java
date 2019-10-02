package com.epichust.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epichust.dao.StudentDao;
import com.epichust.entity.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class JpaTest
{	
	@Autowired
	private StudentDao dao;
	
	@Test
	public void test() {
		List<Student> list = dao.getAll();
		for (Student student : list)
		{
			System.out.println(student);
		}
	}
	
}
