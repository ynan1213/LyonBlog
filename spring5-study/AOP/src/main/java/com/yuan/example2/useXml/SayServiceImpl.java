package com.epichust.example2.useXml;

public class SayServiceImpl implements SayService {

	@Override
	public void say(String str1, String str2) {
		System.out.println("执行目标方法：" + str1 + "-" + str2);
	}

//	@Override
//	public void say(String str1) {
//		System.out.println("执行目标方法：" + str1);
//	}

}
