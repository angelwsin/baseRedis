<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>
      <form action="/fenbushi/im/send" method="post">
           form:<input name="from" type="text"><br>
           to：<input name="to" type="text"><br>
           content：<input name="content" type="text"><br>
          <input type="submit" value="添加">  
      </form>
</body>
</html>