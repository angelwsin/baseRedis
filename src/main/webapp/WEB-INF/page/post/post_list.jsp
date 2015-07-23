<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>
       <table>
         <tbody>
             <c:forEach items="${postList}" var="post">
               <a href="<%=basePath%>/post/detail/${post.id}?">${post.title }</a><br>
             </c:forEach>
           
         </tbody>
       </table>
</body>
</html>