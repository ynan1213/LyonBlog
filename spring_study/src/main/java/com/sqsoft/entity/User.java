package com.sqsoft.entity;

public class User {

	private String name;
	private Dog dog;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dog getDog() {
		return dog;
	}

	public void setDog(Dog dog) {
		this.dog = dog;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", dog=" + dog + "]";
	}

}
