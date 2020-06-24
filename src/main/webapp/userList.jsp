<%@ page import="java.util.List" %>
<%@ page import="cn.pojo.user" %><%--
  Created by IntelliJ IDEA.
  User: 101-01-0192
  Date: 2020/6/23
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        List<user> list=(List<user>)request.getAttribute("users");
        for (user user : list) {
            out.write(user.getUsername());
        }
    %>

<%--
EL 与 jstl 相关学习资料
EL
点操作符 .
中括号操作符 []



--%>



</body>
</html>
