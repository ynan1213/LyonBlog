package com.ynan;

public class PrintService {

	private String msg;

	public PrintService(String msg) {
		this.msg = msg;
	}

	public void print() {
		System.out.println(" ---------------- spring test --------------- " + msg);
	}

}
