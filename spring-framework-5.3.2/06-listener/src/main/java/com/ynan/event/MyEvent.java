package com.ynan.event;

import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private String msg;

	public MyEvent(Object source, String msg) {
		super(source);
		this.msg = msg;
	}

	public void print() {
		System.out.println(msg);
	}

}
