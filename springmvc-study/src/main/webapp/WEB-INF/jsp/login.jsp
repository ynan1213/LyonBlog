<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录页面</title>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrap.css"/>--%>
</head>
<body>
<h1>登录页面</h1>

<hr/>
<form action="${pageContext.request.contextPath }/submit" class="form-inline">
    <label>用户名:</label>
    <input type="text" name="username" class="form-control" />
    <label>密码:</label>
    <input type="password" name="password" class="form-control"/>
    <label>生日:</label>
    <input type="text" name="birthday" class="form-control" />
    <input type="submit" value="表单get提交" class="btn btn-primary">
</form>
<br/><br/>
<hr/>
<form action="${pageContext.request.contextPath }/submit" method="post" class="form-inline">
    <label>用户名:</label>
    <input type="text" name="username" class="form-control" />
    <label>密码:</label>
    <input type="password" name="password" class="form-control"/>
    <label>生日:</label>
    <input type="text" name="birthday" class="form-control" />
    <input type="submit" value="表单post提交" class="btn btn-primary">
</form>

</body>
</html>