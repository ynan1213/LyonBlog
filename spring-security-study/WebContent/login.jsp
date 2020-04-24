<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring security page</title>
<link rel="stylesheet" href="css/bootstrap.css"/>
</head>
<body>
	<form method="post" action="${pageContext.request.contextPath}/login">
		<div class="form-group">
			<label for="Username">Username</label> 
			<input type="text" class="form-control" name="username" id="Username" placeholder="Username">
		</div>
		<div class="form-group">
			<label for="Password">Password</label> 
			<input type="password" class="form-control" name="password" id="Password" placeholder="Password">
		</div>
		<input type="submit" value="提交">
	</form>
</body>
</html>