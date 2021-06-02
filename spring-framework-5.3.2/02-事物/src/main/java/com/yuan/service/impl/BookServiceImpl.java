package com.epichust.service.impl;

import java.util.List;

import com.epichust.entity.Book;
import com.epichust.dao.BookDao;
import com.epichust.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class BookServiceImpl implements BookService
{
	@Autowired
	@Qualifier("bookServiceImpl2")
	private BookService bookImpl2;

	@Autowired
	private BookDao dao;

	@Transactional
	public int insert(Book book) throws Exception
	{
		int i = bookImpl2.insert(book);

		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){
			@Override
			public void beforeCommit(boolean readOnly)
			{
				System.out.println("beforeCommit");
			}

			@Override
			public void beforeCompletion()
			{
				System.out.println("beforeCompletion");
			}

			@Override
			public void afterCommit()
			{
				System.out.println("afterCommit");
			}

			@Override
			public void afterCompletion(int status)
			{
				System.out.println("afterCompletion");
			}
		});

		return i;
	}


	/**
	 *  异常不匹配不会导致事物回滚，而是会正常提交事物
	 */
//	@Transactional
//	public int insert(Book book) throws Exception
//	{
//		Book book1 = new Book("111111", 111111);
//		dao.insert(book1);
//		if(true){
//			throw new FileNotFoundException("xxxxxxxx");
//		}
//		return 1;
//	}

	@Transactional
	public Book select(int id)
	{
		return bookImpl2.select(id);
	}

	public List<Book> selectAll()
	{
		return null;
	}
}
