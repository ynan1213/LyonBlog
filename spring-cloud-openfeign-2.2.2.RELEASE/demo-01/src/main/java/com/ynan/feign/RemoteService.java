package com.ynan.feign;

import com.ynan.config.XxxFallback;
import com.ynan.entity.User;
import feign.Request.Options;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author yuannan
 * @Date 2021/11/21 11:20
 */
@FeignClient(
	value = "remote-demo",
	/*url = "http://localhost:8002",*/
	contextId = "aaa",
	path = "/remote",
	/*configuration = XxxCustomConfiguration.class,*/
	/*fallbackFactory = XxxFallbackFactory.class,*/
	fallback = XxxFallback.class
)
public interface RemoteService {

	/**
	 * SpringMvcContract在解析的时候，有几个注意点：
	 * 1. method 如果不配置，默认就是GET，但是不能配置多个，比如post、get，多个就会抛异常；
	 * 2. 同样的path只能配置一个值，path可以配置${}占位符；
	 * 3. produces、consumes、headers在这里同样也会生效，都可以配置多个，但是只有第一个会生效
	 */
	@RequestMapping(
		value = "/name",
		//produces = "application/xml",
		consumes = "application/json"
	)
	User remote(@RequestParam String name, @RequestParam("user") User user, String address);

	/**
	 * 方法返回值可以直接 Response 类型
	 */
	@RequestMapping("/name")
	Response remote1(@RequestParam String name, @RequestParam("user") User user, String address);

	/**
	 * 启动报错：因为@RequestBody和没有注解的参数会被序列到请求体body中，一个请求只有一个请求体，所以会报错
	 */
	//@PostMapping("/aaa")
	//String aaa(@RequestParam String name, @RequestBody User user, String address);

	/**
	 * 可以在参数中传入 Options 对象，实现对单个接口的超时设置
	 */
	@GetMapping("/bbb")
	String bbb(@RequestParam String name, Options options);

	/**
	 * 这样不会报错，难道get请求也可以发送请求体吗？
	 */
	@GetMapping("/aaa")
	String aaa(@RequestParam String name, @RequestBody User user);

	// 接口方法会报错
	//	String bbb(String bbb);

	// default 方法会被忽略
	default String ccc() {
		return "ccc";
	}
}
