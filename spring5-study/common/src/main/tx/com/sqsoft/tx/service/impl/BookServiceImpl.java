package com.sqsoft.tx.service.impl;

import java.util.List;

import com.sqfost.exception.CustomException;
import com.sqsoft.tx.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.sqsoft.tx.entity.Book;
import com.sqsoft.tx.service.BookService;


public class BookServiceImpl implements BookService{
	
	@Autowired
	@Qualifier("bookServiceImpl2")
	private BookService bookImpl2;

	@Autowired
	private BookDao dao;

	@Transactional
	public int insert(Book book) throws Exception {
		Book book1 = new Book("111111",111111);
		dao.insert(book1);
		bookImpl2.insert(book);
		throw new CustomException();
		//return 1;
	}

	@Transactional
	public Book select(int id) {
		return bookImpl2.select(id);
	}

	public List<Book> selectAll() {
		return null;
	}
}
