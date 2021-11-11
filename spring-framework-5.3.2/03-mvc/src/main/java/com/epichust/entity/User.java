package com.epichust.entity;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

public class User {

	@NotNull(message = "name不能为空")
	private String name;

	@NotNull(message = "age不能为空111")
	@Positive(message = "age非法")
	private Integer age;

	@NotEmpty(message = "innerList不能为空")
	@Valid
	private List<Inner> innerList;

	public User() {
	}

	public User(String name, int age) {
		this.name = name;
		this.age = age;
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

	public void setInnerList(List<Inner> innerList) {
		this.innerList = innerList;
	}

	public List<Inner> getInnerList() {
		return innerList;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}

}

class Inner {

	@NotBlank(message = "inner name 不能为空")
	private String name;

	@NotNull(message = "inner name 不能为空")
	private Integer age;

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
}