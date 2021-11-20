package com.ynan._07.reflectionUtils;

/**
 * @Author yuannan
 * @Date 2021/11/17 10:06
 */
public class XiaoMing extends Student implements Man {

	@Override
	public String getName() {
		return "xiaoming";
	}

	@Override
	public Integer getAge() {
		return 18;
	}

	@Override
	Integer getGrade() {
		return 6;
	}

	public void play() {
		System.out.println("篮球");
	}

	@Override
	public String getClassName() {
		return super.getClassName();
	}
}
