package com.epichust.service;

import java.util.List;

import com.epichust.entity.Book;

public interface BookService
{
	int insert(Book book) throws Exception;

	Book select(int id);

	List<Book> selectAll();
}
