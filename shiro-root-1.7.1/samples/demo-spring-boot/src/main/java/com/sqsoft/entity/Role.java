package com.sqsoft.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Role implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer rid;

	private String rname;

	private Set<User> users = new HashSet<User>();
	private Set<Module> Modules = new HashSet<Module>();

	public Role() {
		super();
	}

	public Role(Integer rid, String rname) {
		super();
		this.rid = rid;
		this.rname = rname;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Module> getModules() {
		return Modules;
	}

	public void setModules(Set<Module> modules) {
		Modules = modules;
	}

}
