package cn.web;

import cn.config.SpringConfiguration;

import cn.service.IHelloService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
web.xml 中




 */
@WebServlet(name = "helloServlet",urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    /**
     * 成功打印出文字 但是现在有一下问题
     * 1：每一个servlet都要手动初始化spring容器，然后从容器中获取service层实现类 如果有很多歌servlet 的话就要初始化很多次spring容器
     * 2：浪费资源 每一次初始化容器的时候都创建了新的容器对象 消耗了资源 降低了性能
     * 3：效率低 spring容器只有在客户端发送请求，请求到达服务段后才初始化 spring容器 效率不高
     *
     * 解决思路 保证容器只有一个 并且在应用加载的时候启动 应用卸载的时候销毁
     *
     * spring提供了一个监听器 ContextLoaderListener 该监听器会帮助我们初始化一个全局唯一的spring容器
     * 我们通过WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
     * 来获取spring容器
     *
     *
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo
        /*AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        IHelloService helloServiceImpl = (IHelloService)annotationConfigApplicationContext.getBean("helloServiceImpl");
        helloServiceImpl.sayHello();*/

        //todo 通过工具类获取spring容器的方式
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        IHelloService helloServiceImpl = (IHelloService)webApplicationContext.getBean("helloServiceImpl");
        helloServiceImpl.sayHello();

    }
}
