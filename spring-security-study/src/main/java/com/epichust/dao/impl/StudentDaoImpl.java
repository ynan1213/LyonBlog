package com.epichust.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epichust.dao.StudentDao;
import com.epichust.entity.Student;

@Repository
public class StudentDaoImpl implements StudentDao
{
	@Autowired
	private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getAll()
	{
		String hql = "from Student";
		List<Student> list = getSession().createQuery(hql).list();
		return list;
	}

	@Override
	public Student getById(String id)
	{
		Student stu = getSession().get(Student.class, id);
		
		return stu;
	}

}
