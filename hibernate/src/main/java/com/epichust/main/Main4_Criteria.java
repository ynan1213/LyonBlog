package com.epichust.main;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.epichust.entity.Student;

public class Main4_Criteria
{
	public static void main(String[] args)
	{
		Session session = HibernateUtil.openSesson();
		
		Criteria c = session.createCriteria(Student.class);
		c.createAlias("taecher", "t");
		ProjectionList projectionList = Projections.projectionList();
		
//		projectionList.add(Projections.property("name").as("sname"));
		projectionList.add(Projections.property("t.name").as("tname"));
		
		c.setProjection(projectionList);
		
		List list = c.list();
		for (Object s : list)
		{
			System.out.println(s);
		}
		
		HibernateUtil.Commit();
	}

}
