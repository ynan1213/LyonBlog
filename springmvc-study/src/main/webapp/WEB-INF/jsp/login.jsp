<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
</head>
<body>
	<h1>登录页面</h1>
	
	<hr/>
	<form action="${pageContext.request.contextPath }/submit"">
		用户名:<input type="text" name="username"></input><br/>
		密码:<input type="password" name="password"></input><br/>
		<input type="submit" value="表单get提交">
	</form>
	<br/><br/><hr/>
	<form action="${pageContext.request.contextPath }/submit" method="post">
		用户名:<input type="text" name="username"></input><br/>
		密码:<input type="password" name="password"></input><br/>
		<input type="submit" value="表单post提交">
	</form>
</body>
</html>