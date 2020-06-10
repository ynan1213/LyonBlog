package com.sqsoft.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.management.ValueExp;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class User {

	private String username;

	@NumberFormat(pattern = "#,###.##")
	private Integer password;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	@Email(message = "非法邮箱")
	private String email;

	public User() {
		super();
	}
	public User(String username, Integer password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getPassword() {
		return password;
	}

	public void setPassword(Integer password) {
		this.password = password;
	}

	public Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	@Override
	public String toString()
	{
		return "User{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", birthday=" + birthday +
				'}';
	}

}
