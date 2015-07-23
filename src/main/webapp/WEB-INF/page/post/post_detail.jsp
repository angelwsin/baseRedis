<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>
       ${post.title }<br>点击次数${view }<br>
       ${post.content }
       
       評論:<br>
       <c:forEach items="${comments }" var="c">
          ${c.content }<br>
          
       </c:forEach>
        <form action="/fenbushi/comment/add/${post.id }" method="post">
           content：<input name="content" type="text"><br>
            content：<input name="author" type="text"><br>
          <input type="submit" value="添加">
      
      </form>
</body>
</html>