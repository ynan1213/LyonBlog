package com.sqsoft.tx.service;

import java.util.List;

import com.sqsoft.tx.entity.Book;

public interface BookService {

	int insert(Book book) throws Exception;

	Book select(int id);

	List<Book> selectAll();

}
