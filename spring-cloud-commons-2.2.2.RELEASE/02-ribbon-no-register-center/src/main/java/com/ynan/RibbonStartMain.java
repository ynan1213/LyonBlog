package com.ynan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author yuannan
 * @Date 2022/1/4 15:41
 */
@SpringBootApplication
@RestController
public class RibbonStartMain {

	@Autowired
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RibbonStartMain.class);
	}

	@RequestMapping("/server")
	public String remote() {
		return restTemplate.getForObject("http://test-server/remote", String.class);
	}

}
