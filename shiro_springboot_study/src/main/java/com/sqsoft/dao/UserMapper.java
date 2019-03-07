package com.sqsoft.dao;

import com.sqsoft.entity.User;

public interface UserMapper {

	public User selectByPrimaryKey(int uid);

	public User queryUserName(String username);

	public int deleteByPrimaryKey(int uid);

	public int insert(User user);

	public int insertSelective(User user);

	public int updateByPrimaryKeySelective(int uid);

	public int updateByPrimaryKey(int uid);
}
