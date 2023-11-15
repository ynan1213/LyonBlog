package com.epichust.service.impl;

import com.epichust.dao.BookDao;
import com.epichust.entity.Book;
import com.epichust.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao dao;

	@Autowired
	private BookService bookServiceImpl2;

	@Transactional(readOnly = true)
	public int insert(Book book) throws Exception {
		int insert = dao.insert(book);
		System.out.println("外层事物---- before");
		//		try {
		//			bookServiceImpl2.insert(book);
		//		} catch (Exception e) {
		//
		//		}
		//		if(1 == 1)
		//		{
		//			throw new FileNotFoundException("xxx");
		//		}
		System.out.println("外层事物---- after");
		return 1;
	}

	@Transactional
	public Book select(int id) {
		System.out.println("内层事物执行-----------");
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		// throw new RuntimeException("xxx");
		return null;
	}


	public List<Book> selectAll() {
		return null;
	}

	@Override
	@Transactional(timeout = 300)
	public int update() {
		return dao.update();
	}
}
