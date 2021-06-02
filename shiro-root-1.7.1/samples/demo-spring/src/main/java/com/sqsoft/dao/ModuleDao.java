package com.sqsoft.dao;

import com.sqsoft.entity.Module;

public interface ModuleDao {
	
	public Module selectByPrimaryKey(int mid);
	
	public int deleteByPrimaryKey(int mid);
	
	public int insert(Module module);
	
	public int insertSelective(Module module);
	
	public int updateByPrimaryKeySelective(Module module);
	
	public int updateByPrimaryKey(Module module);
	
}
