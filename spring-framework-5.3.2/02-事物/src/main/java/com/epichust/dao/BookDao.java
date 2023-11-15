package com.epichust.dao;

import java.util.List;


import com.epichust.entity.Book;

//@Mapper
public interface BookDao {
	
	int insert(Book book);
	
	Book select(int id);
	
	List<Book> selectAll();

	int update();
}
