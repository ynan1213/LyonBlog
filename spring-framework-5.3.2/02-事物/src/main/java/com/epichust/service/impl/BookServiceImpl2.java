package com.epichust.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.epichust.entity.Book;
import com.epichust.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epichust.dao.BookDao;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class BookServiceImpl2 implements BookService
{
	@Autowired
	private BookDao dao;

	@Transactional(timeout = 1)
	public int insert(Book book) throws InterruptedException
	{
		// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		TimeUnit.SECONDS.sleep(2);
		return dao.insert(book);
	}

	@Transactional
	public Book select(int id)
	{
		return dao.select(id);
	}

	@Override
	public List<Book> selectAll()
	{
		return null;
	}

}
