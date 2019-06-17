package com.sqsoft.dao;

import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.sqsoft.entity.UserDO;

public interface UserDao {
	Set<UserDO> selectByCondition(@Param("addr")String addr, @Param("age")int age, RowBounds rb, @Param("school")String school);
//	Set<UserDO> selectByCondition(String addr, int age, RowBounds rb, @Param("school")String school);
	
	Set<UserDO> selectByUser(UserDO user);
	
	Set<UserDO> selectOne(String addr);
	
	int insert(Map<String,Object> user);
	
	int insert1(Map<String,Object> user);
}
