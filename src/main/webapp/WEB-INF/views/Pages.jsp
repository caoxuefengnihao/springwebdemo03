<%@ page import="cn.pojo.Pages" %>
<%@ page import="cn.pojo.stu" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: caoxuefeng
  Date: 2020/7/18
  Time: 4:59 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
Pages p  = (Pages) request.getAttribute("p");
List<stu> list = p.getList();
    for (stu stu : list) {
        out.print(stu);
    }
%>
<a href="/handler/show40.do?currentPage=0">首页</a>
<a href="/handler/show40.do?currentPage=<%=p.getCurrentPage()-1%>">上一页</a>
<a href="/handler/show40.do?currentPage=<%=p.getCurrentPage()+1%>">下一页</a>
<a href="/handler/show40.do?currentPage=<%=p.getTotalPage()-1%>">尾页</a>

</body>
</html>
