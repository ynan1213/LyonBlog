package com.sqsoft.tx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.sqsoft.tx.entity.Book;
import com.sqsoft.tx.service.BookService;


public class BookServiceImpl implements BookService{
	
	@Autowired
	@Qualifier("bookServiceImpl2")
	private BookService bookImpl2;
	
	@Transactional
	public int insert(Book book) throws Exception {
		return bookImpl2.insert(book);
	}

	@Transactional
	public Book select(int id) {
		return bookImpl2.select(id);
	}

	public List<Book> selectAll() {
		return null;
	}
}
