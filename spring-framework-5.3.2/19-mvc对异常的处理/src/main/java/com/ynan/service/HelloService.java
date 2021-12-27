package com.ynan.service;

import com.ynan.exception.MyException;

/**
 * @Author yuannan
 * @Date 2021/12/27 13:56
 */
public interface HelloService {

	void hello() throws MyException;

	void world();
}
