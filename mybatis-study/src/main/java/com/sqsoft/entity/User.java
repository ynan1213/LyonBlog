package com.sqsoft.entity;

import java.io.Serializable;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int uid;
	private String uname;
	private int uage;
	private String addr;
	private String school;

	public User() {
		super();
	}

	public User(int uid, String uname, int uage, String addr, String school) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.uage = uage;
		this.addr = addr;
		this.school = school;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public int getUage() {
		return uage;
	}

	public void setUage(int uage) {
		this.uage = uage;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Override
	public String toString()
	{
		return "User{" +
				"uid=" + uid +
				", uname='" + uname + '\'' +
				", uage=" + uage +
				", addr='" + addr + '\'' +
				", school='" + school + '\'' +
				'}';
	}
}
