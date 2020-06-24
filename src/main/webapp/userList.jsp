<%@ page import="java.util.List" %>
<%@ page import="cn.pojo.user" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: 101-01-0192
  Date: 2020/6/23
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table cellpadding=0 cellspacing=0 border="1">
    <thead>
    <tr>
        <th>UserName</th>
    </tr>
    </thead>
    <tbody>
    <%
        ArrayList<user> users = (ArrayList<user>)request.getAttribute("users");
        for (user user : users) {
            out.println("<tr><td>"+user.getUsername()+"</td></tr>");
        }
    %>


    <%--<c:forEach items="${users}" var="user">
        <tr><td>${user.username}</td></tr>
    </c:forEach>--%>
    </tbody>
</table>
<%--
EL 与 jstl 相关学习资料
EL ${}
点操作符 .
中括号操作符 [] 可以访问数组 可以避免特殊字符
通过el获取map属性

运算符  关系与逻辑运算符

empty运算符  判断是否为空 是空返回true  反之为false
el表达式的隐式对象  不需要new 自带的对象
a 作用域访问对象
pageScope requestScope sessionScope 如果不指定域对象 则从小到大 一次遍历 直到找到
b 参数访问对象
用于获取表单数据 ${param.表单的name属性值}  数组多选框 ${paramValues.表单的name属性值}  还有超链接的参数值 地址栏里面的参数值
c jsp隐式对象
pageContext

jstl 比 el更加强大

--%>



</body>
</html>
