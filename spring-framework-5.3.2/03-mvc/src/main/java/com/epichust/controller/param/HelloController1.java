package com.epichust.controller.param;

import com.epichust.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author yuannan
 * @Date 2021/10/31 17:15
 */
@Controller
public class HelloController1 {

	@RequestMapping("/aaa")
	@ResponseBody
	public String aaa(String str, int i, boolean flag) {
		return "aaa";
	}

	@RequestMapping("/bbb")
	@ResponseBody
	public String bbb(@RequestParam User user) {
		return "bbb";
	}

	@RequestMapping("/ccc")
	@ResponseBody
	public String ccc(@RequestBody User user, @RequestBody User user1, @RequestParam String name) {
		return "ccc";
	}

}
