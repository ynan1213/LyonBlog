package com.ynan.exception;

/**
 * @Author yuannan
 * @Date 2021/12/27 11:34
 */
public class MyRuntimeException extends RuntimeException {

	static final long serialVersionUID = -7034897190712366939L;

	public MyRuntimeException(String str) {
		super(str);
	}
}
