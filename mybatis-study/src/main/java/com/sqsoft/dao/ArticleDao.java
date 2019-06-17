package com.sqsoft.dao;

import org.apache.ibatis.annotations.Param;

import com.sqsoft.entity.ArticleDO;

public interface ArticleDao {
	
	ArticleDO findOne(@Param("id") int id);
	
}
