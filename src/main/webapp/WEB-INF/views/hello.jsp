<%--
  Created by IntelliJ IDEA.
  User: 101-01-0192
  Date: 2020/6/22
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    String s = (String)request.getAttribute("msg");
%>
<h1><%= s%></h1>


</body>
</html>
