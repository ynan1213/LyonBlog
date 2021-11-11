package com.ynan._04.DateTimeFormatAnno;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author yuannan
 * @Date 2021/10/30 11:43
 */
public class Person {

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date birthday;

	public Person(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
