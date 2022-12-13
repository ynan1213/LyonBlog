package com.ynan;

import com.ynan.service.UserService;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yuannan
 */
@ComponentScan("com.ynan.service")
public class RootConfig extends ParentRootConfig {

	@Autowired
	private UserService userTwoServiceImpl;

	@Autowired
	public String userOneServiceImpl(UserService userOneServiceImpl) {
		return "";
	}

	@Value("${xxx}")
	private String xxx;

	@Value("${xxx}")
	public String userOneServiceImpl1(String xxx) {
		return xxx;
	}

	@Resource(name = "userOneServiceImpl", type = UserService.class)
	private UserService userService;

	@Resource
	public String userTwoServiceImpl(UserService userService) {
		return "";
	}


	@PostConstruct
	public String aaa() {
		System.out.println("@PostConstruct ...");
		return "aaa";
	}

	@Autowired(required = false)
	public RootConfig(UserService userService) {
		System.out.println("----------- 一个参数构造 ------------");
	}

	@Autowired(required = false)
	public RootConfig(@Value("${xxx}") String msg, UserService userTwoServiceImpl, ApplicationContext context) {
		System.out.println("----------- 两个参数构造 ------------");
	}

	//	private RootConfig() {
	//		System.out.println("----------- 无参构造 -----------");
	//	}

}
