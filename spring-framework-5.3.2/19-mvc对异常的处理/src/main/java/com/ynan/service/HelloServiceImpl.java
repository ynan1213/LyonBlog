package com.ynan.service;

import com.ynan.advice.IServiceProxy;
import com.ynan.exception.MyException;
import com.ynan.exception.MyRuntimeException;
import java.lang.reflect.Proxy;
import org.springframework.stereotype.Service;

/**
 * @Author yuannan
 * @Date 2021/12/27 13:56
 */
@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public void hello() throws MyException {
		throw new MyException("hello 方法出现 runtimeException");
	}

	@Override
	public void world() {
		// 这里会抛出 UndeclaredThrowableException，详情见  https://segmentfault.com/a/1190000012262244
		HelloServiceImpl helloService = new HelloServiceImpl();
		HelloService proxyInstance = (HelloService) Proxy.newProxyInstance(helloService.getClass().getClassLoader(),
				helloService.getClass().getInterfaces(),
				new IServiceProxy(helloService));
		try {
			proxyInstance.hello();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
}
