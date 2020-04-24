package com.epichust.main;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class TimerTest implements JavaDelegate
{
	
	
	public TimerTest()
	{
		super();
		System.out.println("初始化============================");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("aaaaaaaa11111111111111111111111111111");
	}
}
