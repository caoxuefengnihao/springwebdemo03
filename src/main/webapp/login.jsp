<%--
  Created by IntelliJ IDEA.
  User: 101-01-0192
  Date: 2020/6/22
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>登录页面</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/login.css" rel="stylesheet">
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.js"></script>
</head>
<body>


<%
    String username = "";
    String password = "";
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
        String name = cookie.getName();
        if("username".equals(name)){
            username = cookie.getValue();
        }
        if("password".equals(name)){
            password = cookie.getValue();
        }
    }
%>
<h1 class="form-signin">登录</h1>
<div class="container text-center">
    <form class="form-signin" action="/demo06">
        <h2 class="form-signin-heading">登录页面</h2>
        <input type="text"  name="username" class="form-control" placeholder="用户名" value="<%=username%>">
        <input type="password"  name="password" class="form-control" placeholder="密码" value="<%=password%>">
        <input type="checkbox"  name="remember" value="remember">记住账号和密码
        <button class="btn btn-lg btn-primary btn-block" type="submit" >登录</button>
        <button class="btn btn-lg btn-primary btn-block" type="reset">取消</button>
    </form>
</div>

<h1 class="form-signin">注册</h1>
<div class="container text-center">
    <form class="form-signin" action="/demo06">
        <h2 class="form-signin-heading">注册页面</h2>
        <input type="text"  name="username" class="form-control" placeholder="用户名">
        <input type="password"  name="password" class="form-control" placeholder="密码">
        <button class="btn btn-lg btn-primary btn-block" type="submit" >注册</button>
        <button class="btn btn-lg btn-primary btn-block" type="reset">取消</button>
    </form>
</div>



</body>
</html>
