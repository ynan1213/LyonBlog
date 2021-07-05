package com.ynan.service;

import com.ynan.entity.User;

public interface Dao
{
	User select(String ssss, int i, User user);

	void insert();
}
