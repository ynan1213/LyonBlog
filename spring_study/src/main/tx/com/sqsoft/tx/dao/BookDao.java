package com.sqsoft.tx.dao;

import java.util.List;


import com.sqsoft.tx.entity.Book;

//@Mapper
public interface BookDao {
	
	int insert(Book book); 
	
	Book select(int id);
	
	List<Book> selectAll();
	
}
