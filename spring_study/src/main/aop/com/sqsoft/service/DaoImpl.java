package com.sqsoft.service;

public class DaoImpl implements Dao{

	@Override
	public void select() {
		System.out.println("DaoImpl select-----------------------");
	}

	@Override
	public void insert() {
		System.out.println("DaoImpl insert-----------------------");
	}

}
