package com.ynan._07.reflectionUtils;

/**
 * @Author yuannan
 * @Date 2021/11/17 10:04
 */
public interface Man {

	String getName();

	Integer getAge();

	default String getAddress(){
		return "ShangHai";
	}
}
