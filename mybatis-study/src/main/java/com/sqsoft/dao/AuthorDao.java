package com.sqsoft.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.sqsoft.entity.AuthorDO;

public interface AuthorDao {
	
	List<AuthorDO> findOne(@Param("xxx")int id,String s,RowBounds rb, String ss);
	
}
