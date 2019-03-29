package com.sqsoft.tx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sqsoft.tx.dao.BookDao;
import com.sqsoft.tx.entity.Book;
import com.sqsoft.tx.service.BookService;

public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDao dao;
	
	@Override
	public int insert(Book book) {
		return dao.insert(book);
	}

	@Override
	public Book select(int id) {
		return dao.select(id);
	}

	@Override
	public List<Book> selectAll() {
		return dao.selectAll();
	}

}
