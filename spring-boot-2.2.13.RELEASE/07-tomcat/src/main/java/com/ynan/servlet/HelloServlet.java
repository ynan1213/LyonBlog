package com.ynan.servlet;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author yuannan
 */
public class HelloServlet implements Servlet {

	public HelloServlet() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("init");
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		ServletOutputStream outputStream = res.getOutputStream();

		// 方式一：浏览器不会理解接收到内容
		outputStream.write("hello world !!!!!!!!!!!!".getBytes());
		res.flushBuffer();
		outputStream.flush();

		// 方式二：参照springMVC，浏览器仍然不会立即接收到内容
		Writer writer = new OutputStreamWriter(outputStream, UTF_8);
		writer.write("hello world !!!!!!!!!!!!");
		writer.flush();


		System.out.println("service");
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void destroy() {

	}
}
