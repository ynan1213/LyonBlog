package com.sqsoft.entity;

import java.io.Serializable;
import java.util.List;

public class AuthorDO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String name;
	
	private Integer age;
	
	private SexEnum sex;
	
	private String email;
	
	private List<ArticleDO> articles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public SexEnum getSex() {
		return sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ArticleDO> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleDO> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "AuthorDO [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + ", email=" + email
				+ ", articles=" + articles + "]";
	}
}
