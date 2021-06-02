package com.sqsoft.entity;

import java.io.Serializable;
import java.util.Set;

public class Module implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer mid;
	 
    private String mname;
 
    private Set<Role> roles;
    
	public Module() {
		super();
	}

	public Module(Integer mid, String mname) {
		super();
		this.mid = mid;
		this.mname = mname;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
    
}
