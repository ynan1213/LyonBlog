package com.sqsoft.dao;

import com.sqsoft.entity.User;

public interface UserDao {

	User selectByUid(int uid);
	
	User selectByName(String username);

}
