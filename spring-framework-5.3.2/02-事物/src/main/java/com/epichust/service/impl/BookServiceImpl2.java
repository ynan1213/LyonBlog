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
public class BookServiceImpl2 implements BookService {

	@Autowired
	private BookDao dao;

	@Transactional
	public int insert(Book book) throws InterruptedException {
		System.out.println("内层事务开始执行");
		// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		// TimeUnit.SECONDS.sleep(2);
		//dao.insert(book);
		if (1 == 1) throw new RuntimeException("xxx");
		return 1;
	}

	@Transactional
	public Book select(int id) {
		return dao.select(id);
	}

	@Override
	public List<Book> selectAll() {
		return null;
	}

	@Override
	public int update() {
		return 0;
	}

}
