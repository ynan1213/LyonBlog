package com.epichust.main;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.epichust.entity.Student;
import com.epichust.entity.Teacher;

public class Main3_OneToMany
{
	public static void main(String[] args)
	{
		Session session = HibernateUtil.openSesson();
		
		Teacher t = (Teacher)session.get(Teacher.class, "402899816fb2712f016fb27134570001");
		
		Set<Student> students = t.getStudents();
		for (Student s : students)
		{
			System.out.println(s);
		}
		
		HibernateUtil.Commit();
	}

}
