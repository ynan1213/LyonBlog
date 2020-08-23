package com.sqsoft.dao;

import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.sqsoft.entity.User;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserDao {
	Set<User> selectByCondition(@Param("addr")String addr, @Param("age")int age, RowBounds rb, @Param("school")String school);

	Set<User> selectByUser(User user);
	
	Set<User> selectOne(String addr);
	
	int insert(User user);
	
	int insert1(Map<String,Object> user);

	public User getById(int id);

}
