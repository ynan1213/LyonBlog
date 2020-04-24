package com.epichust.main;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.epichust.entity.Student;
import com.epichust.entity.Teacher;

/**
 * 	返回值映射
 */
public class Main2
{
	public static void main(String[] args)
	{
		Session session = HibernateUtil.openSesson();
		
		String hql = "select t.id as id, t.name as name, t.age as age from Student s inner join s.teacher t";
		
//		Query query = session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(Teacher.class));
		Query query = session.createQuery(hql);
		
		List list = query.list();
		for (Object o : list)
		{
			System.out.println(o.toString());
		}
		

		HibernateUtil.Commit();
	}

}
