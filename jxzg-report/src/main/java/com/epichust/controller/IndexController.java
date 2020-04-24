package com.epichust.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.epichust.entity.AjaxData;
import com.epichust.entity.User;
import com.epichust.entity.ValidData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class IndexController
{
	@Value("${remote.reportServerIP}")
	private String reportServerIP;

	@Value("${remote.mesServerIP}")
	private String mesServerIP;

	@RequestMapping({ "/login", "/" })
	public String toLogin()
	{
		return "login";
	}

	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping({ "/index" })
	public String toIndex(HttpServletRequest request,Model model)
	{
		String name = (String)request.getSession().getAttribute("username");
		model.addAttribute("username", name);
		model.addAttribute("reportServerIP", reportServerIP);
		if(request.getSession().getAttribute("locale_lang") != null && request.getSession().getAttribute("locale_lang").equals("en_US"))
		{
			return "index_en";
		}else
		{
			return "index_zh";
		}
	}

	@RequestMapping("/doSubmit")
	@ResponseBody
	public AjaxData doSubmit(@RequestBody User user, HttpServletRequest request)
	{
		//{"data":"用户ynxx 不存在","type":"error"}
		//{"data":"success,MdYkh6KFDkw,true","type":"success"}
		//{"data":"fail,PbDXC2Di2uPEQb4tE7wClg,true","type":"success"}
		
		ValidData result = validAccount(user);
		if("error".equals(result.getType()))
		{
			return new AjaxData(AjaxData.FAIL, result.getData());
		}else 
		{
			if(result.getData().startsWith("success"))
			{
				request.getSession().setAttribute("username", user.getUsername());
				request.getSession().setAttribute("locale_lang", user.getLocale());
				return new AjaxData("success", "/index");
			}else
			{
				return new AjaxData(AjaxData.FAIL,"用户名或密码错误");
			}
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		if(session != null)
		{
			session.setAttribute("username", null);
		}
		return "login";
	}
	
	private ValidData validAccount(User user)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json;charset=utf-8");
        
        Map<String, Object> postData = new HashMap<>();
        postData.put("_u", user.getUsername());
        postData.put("_p", user.getPassword());
		
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(postData, httpHeaders);
        
        //http://localhost:8090/prj-jxzg/padController!login.m?_u=yn1&_p=123456
        String url = "http://" + mesServerIP + "/prj-jxzg/padController!login.m?_u=" 
        		+ user.getUsername() + "&_p=" + user.getPassword();
       
        String jsonStr = restTemplate.postForEntity(url, httpEntity, String.class).getBody();
        try
		{
        	ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(jsonStr, ValidData.class);
		} catch (JsonProcessingException e)
		{
			return new ValidData("error", "后台出错");
		}
	}
	
}
