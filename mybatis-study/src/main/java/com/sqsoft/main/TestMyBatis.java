package com.sqsoft.main;

import java.util.List;

import org.junit.Test;

import com.sqsoft.dao.MailDao;
import com.sqsoft.dao.impl.MailDaoImpl;
import com.sqsoft.entity.Mail;

public class TestMyBatis {

	private static MailDao mailDao;

	static {
		mailDao = new MailDaoImpl();
	}
	
	@Test
	public void testUpdate() {
		Mail mail = new Mail(2, "123@qq.com", "个人使用");
		mail.setId(2);
		System.out.println(mailDao.updateMail(mail));
		System.out.println(mailDao.selectMailById(2));
	}

	@Test
	public void testInsert() {
		Mail mail1 = new Mail(1, "123@sina.com", "个人使用");
		Mail mail2 = new Mail(2, "123@qq.com", "企业使用");
		Mail mail3 = new Mail(3, "123@sohu.com", "注册账号使用");
		System.out.println(mailDao.insertMail(mail1));
		System.out.println(mailDao.insertMail(mail2));
		System.out.println(mailDao.insertMail(mail3));
	}

	@Test
	public void testDelete() {
		System.out.println(mailDao.deleteMail(1));
	}

	@Test
	public void testSelectOne() {
		System.out.println(mailDao.selectMailById(2));
	}

	@Test
	public void testSelectList() {
		List<Mail> mailList = mailDao.selectMailList();
		if (mailList != null && mailList.size() != 0) {
			for (Mail mail : mailList) {
				System.out.println(mail);
			}
		}
	}

}
