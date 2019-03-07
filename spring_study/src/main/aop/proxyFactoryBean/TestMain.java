package proxyFactoryBean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("proxyFactoryBean/proxyFactoryBean.xml");
	    IBussinessService bussinessServiceImpl =  ac.getBean("methodProxy",IBussinessService.class);
        bussinessServiceImpl.bussiness();
        ac.close();
	}
}
