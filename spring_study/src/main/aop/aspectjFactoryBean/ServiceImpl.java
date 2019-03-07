package aspectjFactoryBean;

import org.springframework.stereotype.Component;

@Component
public class ServiceImpl implements Service {

	@Override
	public void select() {
		System.out.println("执行目标方法 select()-------------------------");
	}

	@Override
	public void insert() throws Throwable {
		System.out.println("执行目标方法 insert()-------------------------");
		throw new Exception("aaaaaaaaaaa");
	}


}
