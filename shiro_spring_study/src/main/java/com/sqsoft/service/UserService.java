package com.sqsoft.service;

import com.sqsoft.entity.User;

public interface UserService {
	
	User selectByUid(int uid);
	
	User selectByName(String username);
}
