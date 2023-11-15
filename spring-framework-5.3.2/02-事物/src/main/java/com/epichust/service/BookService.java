package com.epichust.service;

import com.epichust.entity.Book;
import java.util.List;

public interface BookService {

	int insert(Book book) throws Exception;

	Book select(int id);

	List<Book> selectAll();

	int update();
}
