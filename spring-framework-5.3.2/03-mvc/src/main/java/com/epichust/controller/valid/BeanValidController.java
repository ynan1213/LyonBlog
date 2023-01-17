package com.epichust.controller.valid;

import com.epichust.entity.User;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2021/10/12 3:45 下午
 */
@RestController
@Validated
public class BeanValidController {

//	@RequestMapping("/hello")
//	public String hello(@Validated @RequestBody User user) {
//		return user == null ? "null" : user.toString();
//	}

	@RequestMapping("/world")
	public String world(@NotNull(message = "str 不能为空") String str){
		return "hello world";
	}

}
