package com.ynan._03.日期时间格式化;

import java.time.Instant;

/**
 * @Author yuannan
 * @Date 2021/10/30 09:55
 */
public class Jsr310DateMain {

	public static void main(String[] args) {

		Instant now = Instant.now();
		System.out.println(now.getEpochSecond());
		System.out.println(now.getNano());

	}
}
