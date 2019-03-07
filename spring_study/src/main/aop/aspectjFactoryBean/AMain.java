package aspectjFactoryBean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AMain {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:aspectjFactoryBean/application-aop.xml");
		
		Service s = (Service) ac.getBean("serviceImpl");
		s.select();
		ac.close();
	}
	
}
