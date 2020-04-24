package com.epichust.main;

import org.hibernate.Session;

import com.epichust.entity.School;
import com.epichust.entity.Student;
import com.epichust.entity.Teacher;

public class Main5_insert
{
	public static void main(String[] args)
	{
		Session session = HibernateUtil.openSesson();

		
// ===================================  方式一  ===========================================		
//		Teacher t1 = new Teacher("伊鲁卡", "30");
//		t1.setSchool((School)session.get(School.class, "402899816fb25531016fb25539060000"));
//		
//		Teacher t2 = new Teacher("卡卡西", "32");
//		t2.setSchool((School)session.get(School.class, "402899816fb25531016fb25539210001"));
//		
//		Teacher t3 = new Teacher("自来也", "40");
//		t3.setSchool((School)session.get(School.class, "402899816fb25531016fb25539210001"));

// ===================================  方式二  ===========================================			
		
		Teacher t1 = new Teacher("伊鲁卡", "30");
		t1.setSchoolId("402899816fb25531016fb25539060000");
		
		Teacher t2 = new Teacher("卡卡西", "32");
		t2.setSchoolId("402899816fb25531016fb25539210001");
		
		Teacher t3 = new Teacher("自来也", "40");
		t3.setSchoolId("402899816fb25531016fb25539210001");
		
		
		session.save(t1);
		session.save(t2);
		session.save(t3);
		
		
//		Student s1 = new Student("小樱","13");
//		s1.setTeacherId("402899816fb2712f016fb271344b0000");
//		
//		Student s2 = new Student("鸣人","13");
//		s2.setTeacherId("402899816fb2712f016fb27134570001");
//		
//		Student s3 = new Student("佐助","13");
//		s3.setTeacherId("402899816fb2712f016fb27134570001");
//		
//		Student s4 = new Student("波风水门","30");
//		s4.setTeacherId("402899816fb2712f016fb27134570002");
//		
//		Student s5 = new Student("xxxxx","28");
//		s5.setTeacherId("402899816fb2712f016fb27134570002");
//		
//		session.save(s1);
//		session.save(s2);
//		session.save(s3);
//		session.save(s4);
//		session.save(s5);
		
		HibernateUtil.Commit();
	}

}
