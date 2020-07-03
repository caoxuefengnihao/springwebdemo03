package cn.web;

import cn.config.SpringConfiguration;
import cn.pojo.user;
import cn.service.IAccountService;
import cn.service.impl.IAccountServiceimpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "cookieAndSessionDemo",urlPatterns = {"/demo06"})
public class CookieAndSessionDemo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * 1问题 每个用户在使用浏览器与服务器进行会话的过程中 不可避免各自会产生一些数据 程序想要为每个用户
         * 保存这些数据 这就用到了 Cookie与Session技术  Coolie和Session的作用都是保存数据
         *
         * 2 Cookie技术  Cookie是由服务端生成，发送给浏览器，浏览器会将Cookie中的 key/value保存到某个目录下的文本文件内
         * 下次请求同一个网站时就发送该Cookie给服务器 前提是浏览器设置为启用Cookie
         * 用处 登陆页面记住密码功能
         * 3 Cookie的基本API(重点)
         * 方法       使用实例        说明
         * 创建       Cookie(String name,String value)    Cookie c1 = new Cookie("name","tom")
         * 获取name值  String getName()    c1.getName()
         * 获取value值 String getValue()   c1.getValue()
         * 发送cookie void addCookie(Cookie cookie)   response.addCookie(c1)
         * 设置cookie的值   void setValue(String value) c1.setValue("李四")
         * 获取所有cookie   Cookie[] getCookie()    request.getCookies()
         * 4 存活时间与有效路径
         * cookie.setMaxAge(60*60); 设置cookie的最大存活时间
         *
         * 5案例实现 登录时记住用户名与密码  成功
         * 6 cookie删除
         * Session技术 作用 为每个访问服务器的用户创建一个存储数据的容器 容器中的数据在多个请求之间共享
         *  session 是服务器给每一个访问这个服务器的客户端用户创建一个容器 这个容器中存储的数据能够在多个request之间实现共享
         *  而且 这个容器只属于当前这个用户
         *
         * session容器创建
         * request.getSession
         * session常用的api
         * 方法           使用示例            说明
         * void setAttribute(String name,Object value)  session.setAttribute("LoginUser",user)  将一个对象与一个名称关联之后存到session中
         * Object getAttribute(String name) session,getAttribute("LoginUser")   通过名字获取session中的数据
         */
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        IAccountService accountService = (IAccountServiceimpl)annotationConfigApplicationContext.getBean("accountService");

/*        //获取请求中的所有cookie
        Cookie[] cookies = req.getCookies();
        //遍历cookies，获取每一个cookie的name值和value值
        for (Cookie c:cookies) {
            //获取cookie的name值
            String name = c.getName();
            //获取cookie的value值
            String value = c.getValue();
            //打印name值和value值
            System.out.println("Cookie中的name="+name+";value="+value);
        }*/

        String username = req.getParameter("username");
        String passwrod = req.getParameter("password");


        user user = accountService.queryLogin(new user(username,passwrod));
        if(user==null){
            resp.getWriter().write("fail");
        }else{
            //登录成功，在用户勾选记住用户名和密码的情况下使用cookie记住用户名和密码
            String remember = req.getParameter("remember");
            if("remember".equals(remember)){
                //生成cookie信息
                Cookie cookie = new Cookie("username",username);
                Cookie cookie2 = new Cookie("password",passwrod);
                cookie.setMaxAge(60*60);
                cookie2.setMaxAge(60*60);
                resp.addCookie(cookie);
                resp.addCookie(cookie2);
            }
            //第一次调用时创建session对象 再次调用时就去服务器中查找session
            HttpSession session = req.getSession();
            System.out.println(session.getId());
            session.setAttribute("username",username);
            session.setAttribute("password",passwrod);
            resp.sendRedirect("hello.jsp");
        }

        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName()+"--"+cookie.getValue());
        }


    }
}
