package cn.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "response",urlPatterns = {"/demo05"})
public class ResponseDemo2 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //给浏览器响应头设置 Refresh参数，3秒后跳转到http://www.baidu.com

        /**
         *
         */
        resp.setHeader("Refresh","3;url=http://www.baidu.com");

    // demo06 的案例 创建cookie

        Cookie password = new Cookie("password", "123");
        Cookie cookie = new Cookie("username", "zhangsan");
        resp.addCookie(password);
        resp.addCookie(cookie);
    }
}
