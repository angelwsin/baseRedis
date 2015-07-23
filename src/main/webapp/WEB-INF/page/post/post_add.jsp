<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>
      <form action="/fenbushi/post/edit" method="post">
           title:<input name="title" type="text"><br>
           content：<input name="content" type="text"><br>
           author：<input name="author" type="text"><br>
           slug:<input name="slug" type="text">
          <input type="submit" value="添加">
      
      </form>
</body>
</html>