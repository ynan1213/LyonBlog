package com.sqsoft.tx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sqsoft.tx.dao.BookDao;
import com.sqsoft.tx.entity.Book;
import com.sqsoft.tx.service.BookService;

public class BookServiceImpl2 implements BookService
{
	@Autowired
	private BookDao dao;

	@Transactional(readOnly = true)
	public int insert(Book book)
	{
		return dao.insert(book);
	}

	@Transactional()
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
