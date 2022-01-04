package com.epichust.service.impl;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.epichust.entity.Book;
import com.epichust.dao.BookDao;
import com.epichust.service.BookService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class BookServiceImpl implements BookService
{
	@Autowired
	private BookDao dao;

	@Autowired
	@Qualifier("bookServiceImpl2")
	private BookService bookServiceImpl2;

	@Transactional
	public int insert(Book book) throws Exception
	{
		System.out.println("外层事物---- before");
		bookServiceImpl2.insert(book);
//		if(1 == 1)
//		{
//			throw new FileNotFoundException("xxx");
//		}
		System.out.println("外层事物---- after");
		return 1;
	}

	@Transactional
	public Book select(int id)
	{
		System.out.println("内层事物执行-----------");
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		// throw new RuntimeException("xxx");
		return null;
	}


	public List<Book> selectAll()
	{
		return null;
	}
}
