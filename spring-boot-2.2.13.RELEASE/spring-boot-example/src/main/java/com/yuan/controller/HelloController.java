package com.epichust.controller;

import com.epichust.bean.Bean1;
import com.epichust.bean.Bean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController
{

    @Autowired
    private Bean1 bean;

	@Value("xxxxx.yyyyy")
	private String sss;


    @RequestMapping("/xxx")
    @ResponseBody
    public String login()
    {
		System.out.println(sss);
        return bean.print();
    }

}
