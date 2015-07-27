<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>
              ${msg }
           form:<input name="from" type="text" value="${msg.from}"><br>
           to：<input name="to" type="text" value="${msg.to }"><br>
           content：<input name="content" type="text" value="${msg.content}"><br>
          
     
</body>
</html>