package com.epichust.fatherAndSon;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.epichust.fatherAndSon")
@Configuration
public class Main
{
	public static void main(String[] args)
	{
		ApplicationContext ac = new AnnotationConfigApplicationContext(Main.class);
		//Father father = ac.getBean(Father.class);
		//Father father = ac.getBean(Son.class);
		//Father father = ac.getBean("son", Father.class);
		//father.print();

		F f = ac.getBean("getF", F.class);
		f.fff();
	}
}
