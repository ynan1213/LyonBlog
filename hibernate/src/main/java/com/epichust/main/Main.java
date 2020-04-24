package com.epichust.main;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.epichust.entity.Teacher;

/**
 * hql查询
 */
public class Main
{
	public static void main(String[] args)
	{
		Session session = HibernateUtil.openSesson();
		
		//student中的teacher中使用了延迟加载
		//该查询不会触发teacher的查询
		String hql = "select s from Student s";
		
		//内连接
//		String hql = "from Student s inner join s.teacher";
//		String hql = "select s from Student s inner join s.teacher";
		
		//左外连接
//		String hql = "select s from Student s left join s.teacher t";
		
		//错误写法(后面的on重复了)
//		String hql = "select s from Student s inner join Teacher t on s.teacher.id = t.id";
		
		//笛卡尔积
//		String hql = "select s from Student s ,Teacher t";
		
		//where前是两表的笛卡尔积，where后面是字段对应的column的条件
//		String hql = "select s from Student s ,Teacher t where s.teacher.id = t.id ";
		
		//错误写法
//		String hql = "select s from Student s left outer join Teacher t on s.tid = t.id";
		//错误写法
//		String hql = "from Teacher left join Student ";
		
//		String hql = "select t.id,t.name,t.age from Student s inner join s.teacher t";
//		String hql = "select t.id,t.name,t.age from student s,teacher t where s.tid = t.id";
		
//		Query query = session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(Teacher.class));
//		Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(Teacher.class));
		Query query = session.createQuery(hql);
		List list = query.list();
		for (Object o : list)
		{
			System.out.println(o.toString());
		}

		HibernateUtil.Commit();
	}

}
