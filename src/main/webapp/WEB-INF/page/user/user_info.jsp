<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>
     <table>
      <tr><td>姓名</td><td>密码</td></tr>
      <c:forEach items="${userList}" var="user">
              <tr><td>${user.username }</td><td>${user.password }</td></tr>
      </c:forEach>
     </table>
</body>
</html>