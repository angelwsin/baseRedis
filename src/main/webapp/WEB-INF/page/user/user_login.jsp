<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>
      <form action="/fenbushi/user/login" method="post">
           姓名:<input name="username" type="text"><br>
          密码：<input name="password" type="password"><br>
          <input type="submit" value="添加">
      
      </form>
</body>
</html>