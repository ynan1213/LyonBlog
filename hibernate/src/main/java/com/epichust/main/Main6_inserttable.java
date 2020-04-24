package com.epichust.main;

import javax.persistence.JoinColumn;

import org.hibernate.Session;

import com.epichust.entity.Student;
import com.epichust.entity.Teacher;

/**
 * hql查询
 */
public class Main6_inserttable
{
	public static void main(String[] args)
	{
		Session session = HibernateUtil.openSesson();
		
		Student stu = new Student();
		stu.setName("xxx");
		stu.setAge("20");
		
		Teacher tea = (Teacher)session.get(Teacher.class, "402899816fb2712f016fb271344b0000");
		//因为taecher字段设置了@JoinColumn(name = "TID",insertable=false,updatable=false)
		//所以插入失败，同理，更新也会失败
//		stu.setTeacher(tea);

		//成功
		stu.setTeacherId("402899816fb2712f016fb271344b0000");
		
		session.save(stu);
		
		HibernateUtil.Commit();
	}

}
