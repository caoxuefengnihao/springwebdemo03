package cn.handler;


import cn.JavaBean.User;
import cn.pojo.Pages;
import cn.pojo.stu;
import cn.pojo.user;
import cn.service.IAccountService;
import cn.service.IHelloService;
import cn.service.UserService;
import cn.service.hiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("handler")
public class spring {
    @Autowired
    @Qualifier("user")
    user u;
    @Autowired
    @Qualifier("helloServiceImpl")
    IHelloService iHelloService;
    @Autowired
    @Qualifier("accountService")
    IAccountService iAccountService;

    @Autowired
    @Qualifier("hiveServiceImpl")
    hiveService hiveSer;
    @Autowired
    @Qualifier("UserServiceImpl")
    UserService userService;
    @Autowired
    @Qualifier("pages")
    Pages pages;

    /**
     * @RequestMapping("/show1.do"): 使得请求的url可以映射到指定的目标方法上
     * @return
     */
    @RequestMapping("/show1.do")
    public ModelAndView test1(){
        ModelAndView mv = new ModelAndView();
        //设置要显示的页面：视图
        mv.setViewName("hello");
        //添加要显示的数据:数据是存在request域中
        mv.addObject("msg","这是springmvc的第一个程序！");
        return mv;
    }

    @RequestMapping("show2")
    public ModelAndView test2(ModelAndView mv,@RequestParam String username,@RequestParam String password){
        user user = iAccountService.queryLogin(new user(username, password));
        System.out.println(username+"---------"+password);
        if(user == null){
            int zuce = iAccountService.zuce(new user(username, password));
            System.out.println(zuce);
            if(zuce == 0){
                //设置要显示的页面：视图
                mv.setViewName("hello");
                //添加要显示的数据:数据是存在request域中
                mv.addObject("msg","注册成功");
            }else {
                //设置要显示的页面：视图
                mv.setViewName("hello");
                //添加要显示的数据:数据是存在request域中
                mv.addObject("msg","注册失败");
            }
            return mv;
        }else {
            //设置要显示的页面：视图
            mv.setViewName("hello");
            //添加要显示的数据:数据是存在request域中
            mv.addObject("msg","你已经注册");
            return mv;
        }
    }

    @RequestMapping("show3")
    public void test3(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String passwrod = req.getParameter("password");
        u.setUsername(username);
        u.setPassword(passwrod);
        user user = iAccountService.queryLogin(u);
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
            resp.sendRedirect("show39.do");
        }

        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName()+"--"+cookie.getValue());
        }
        System.out.println(req.getParameter("sb"));
    }



    /**
     * show18.do 可以简写成 show18
     */
    @RequestMapping("show18")
    @ResponseStatus(value= HttpStatus.OK)
    public void test18() {
        System.out.println("返回值是void");
    }


    @RequestMapping("show19")
    public ModelAndView test19(){
        iHelloService.sayHello();
        ModelAndView mv = new ModelAndView();
        //设置要显示的页面：视图
        mv.setViewName("hello ");
        //添加要显示的数据:数据是存在request域中
        mv.addObject("msg","这是springmvc的第一个程序！");
        return mv;
    }

    /**
     *
     * 通过@RequestParam("username" defaultParam 为默认值 required 为是否这个参数一定有值)来获取前段form表单传过来的参数
     * 前提条件
     *  由于spring的RequestParam注解接收的参数是来自于requestHeader中，即请求头，也就是在url中，
     *  格式为xxx?username=123&password=456，而RequestBody注解接收的参数则是来自于requestBody中，即请求体中。
     *  因此综上所述，如果为get请求时，后台接收参数的注解应该为RequestParam，如果为post请求时，则后台接收参数的注解就是为RequestBody
     * @param name
     * @param password
     * @return
     */
    @RequestMapping("show20")
    public ModelAndView test20(@RequestParam("username") String name,@RequestParam("password")String password){
        iHelloService.sayHello();
        ModelAndView mv = new ModelAndView();
        System.out.println(name+password);
        //设置要显示的页面：视图
        mv.setViewName("hello");
        //添加要显示的数据:数据是存在request域中
        mv.addObject("msg","这是springmvc的第一个程序！");
        return mv;
    }

    /**
     * 在show20的基础上 我们想把前段form表单传递过来的数据直接解析成 javabean 用来传递数据
     * SpringMVC会将请求参数名和POJO实体中的属性名(set方法)进行自动匹配，
     * 如果名称一致，将把值填充到对象属性中，并且支持级联（例如：user.dept.id）
     *
     * 注意  pojo中的属性必须和表单的属性值一样 可以支持级联
     * @return
     */

    @RequestMapping("show21")
    public ModelAndView test21(user u){
        iHelloService.sayHello();
        ModelAndView mv = new ModelAndView();
        System.out.println(u);
        //设置要显示的页面：视图
        mv.setViewName("hello");
        //添加要显示的数据:数据是存在request域中
        mv.addObject("msg","这是springmvc的第一个程序！");
        return mv;
    }
    /**
     * 关于处理器方法返回值类型的方法
     * 1 返回值为String类型  springMVC默认将返回值视为视图名称
     * 2 返回值为void @ResponseStatus(value=HttpStatus.OK)：如果不响应页面，就需要响应状态
     * 关于接受数据及数据绑定
     * 1 接收servlet的内置对象
     * 2 接收占位符请求路径中的参数 @RequestParam比之@PathVariable的区别是不需要在注解中使用占位符{xxx}
     * 3 接收普通的请求参数
     * 4 获取cookie参数 注解方式获取cookie @CookieValue("key值") 使用方式同@RequestParam
     * 5 基本数据类型的绑定
     * 6 Pojo对象的绑定
     * 7 集合的绑定
     *
     */
    //todo Cookie注解实例
    @RequestMapping("show17")
    public ModelAndView test17(@CookieValue("username") String jsessionid){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");
        modelAndView.addObject("msg",jsessionid);
        return modelAndView;
    }

    /**
     * JSON 在实际的开发工程中 json是最为常见的一种方式 所以spring
     * @ResponseBody	是把Controller方法返回值转化为JSON，称为序列化
     *  当一个处理请求的方法标记为@ResponseBody时， 这个可以配合Ajax 一起使用  后面有Ajax的相关学习资料
     *  表示该方法需要输出其他视图（json、xml），springmvc会通过默认的json转化器转化输出。
     * @RequestBody	是把接收到的JSON数据转化为Pojo对象，称为反序列化
     *
     * @RestController  有时如果在一个Controller中的所有方法都是用来相应json格式的数据 如果有多个方法
     * 就需要在多个方法上使用@ResponseBody 这样太麻烦 这个注解就显示出优势了 将该注解配置在Controller
     * 类上 那么该controller中的所有方法都默认是响应json格式的数据了
     */

    @RequestMapping(value="show23")
    @ResponseBody
    public ArrayList<user> test23() {
        ArrayList<user> list = new ArrayList<user>();
        for(int i = 0;i< 20;i++) {
            user user = new user();
            user.setUsername("zhangsan"+i);
            System.out.println(user);
            list.add(user);
        }
        return list;
    }



    /**
     *  以上的代码实现了请求转发
     *  现在我们要想的是怎么将controler 处理的结果返回给前段页面  我们应该考虑两个方面 视图页面（v）与数据（m）
     *  那么这两个数据怎么保存呢  mvc已经给我们提供了  如果跳转是需要带数据  v,m可以使用以下方式
     *  ModelAndView(既有数据也有视图) ModelMap Map Model  ----数据放在了request的作用域当中
     *
     *
     * @SessionAttributes(types = {"..class"}) 将数据放到session里面 注意这个放到 类的上面
     *  type的意思是将该类型放到 session域里面
     * @ModelAttributes 经常在更新时使用 在任何一次请求钱都会先执行@ModelAttribute修饰的方法 慎用
     *
     */
    @RequestMapping(value="show27")
    public ModelAndView test22() {
        ModelAndView mv = new ModelAndView();
        ArrayList<user> list = new ArrayList<user>();
        for(int i = 0;i< 20;i++) {
            user user = new user();
            user.setUsername("zhangsan"+i);
            System.out.println(user);
            list.add(user);
        }
        mv.setViewName("userList");
        mv.addObject("users",list);
        return mv;
    }

    @RequestMapping(value="show28")
    public void test28(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ArrayList<user> list = new ArrayList<user>();
        for(int i = 0;i< 20;i++) {
            user user = new user();
            user.setUsername("zhangsan"+i);
            System.out.println(user);
            list.add(user);
        }
        req.setAttribute("users",list);
        req.getRequestDispatcher("userList.jsp").forward(req,resp);
    }

    /**
     * 拦截器 一般用于对处理器进行预处理和后处理
     * 应用场景
     *  1 权限检查如登陆检测 进图处理器前用户是否登陆 如果没有登陆直接返回到登陆页面
     *  2 性能监控 有时候系统在某段时间莫名其妙的满 可以通过拦截器在进入处理器之前记录开始时间 在
     *  处理完后记录结束时间 统计处理器执行使用了多少时间
     * springMVC的拦截器接口(HandlerInterceptor)定义了三个方法
     *  preHandle 调用Handler之前执行，称之为预处理回调方法
     *      返回值 true表示放行 后续业务逻辑继续执行
     *             false 表示被拦截 后续业务逻辑不在继续执行 但之前返回true的拦截器的请求完成回调方法会倒序执行
     *  postHandle 顶用Handler之后执行 称为后处理回调方法
     *  afterCompletion 视图渲染完成之后执行 可以称为请求完成回调方法
     *
     * 拦截器的配置过程
     *  1 编写自定义拦截器实现HandlerInterceptor
     *  2 在springMVC-servlet.xml中注册自定义拦截器
     */
    @RequestMapping(value="show35")
    public String test35() {
        System.out.println("自定义处理器正在执行！！");
        return "hello";
    }

    /**
     *
     * 视图和解析器的流程
     * controller返回值
     *      String("")
     *      View          -----> ModelAndView ---->视图解析器ViewResolver(视图解析器的顶级接口) ----> 视图 view(视图的顶级接口) (jsp/html/我们最终能看到的东西) 下面的实现类 主要看两个InternalResourceView JstlView 两个是父子关系
     *      ModelAndView
     *
     * 常见的视图和解析器  例如:将字符串变成一个jsp页面
     *
     * JstlView --实现国际化操作
     *
     *
     */

    /**
     * 要用springMVC实现静态页面的相关转化
     * 一个配置就搞定了
     * <mvc:view-controller path ="请求的路径" view-name = ""></>
     *  以上标签会让所有的请求转入 标签地址 而忽略调@RequestMapping（）
     *  如何解决呢 加一个标签<mvc:annotation-driver></>
     */


    /**
     * 请求转发与重定向
     * 返回值为字符串是 默认为视图名称 当返回值字符串是以forward: 或 redirect: kaitou
     * 则会被认为是转发或者重定向  需要注意的是此种方式 不会被视图解析器加上前缀后缀
     *
     */

    /**
     * 处理静态资源 在配置文件中加一个注解 当然 如果你配置了 拦截*.do文件
     * 就不存在 mvc静态资源加载不出来的情况
     * <mvc:default-servlet-handler></mvc:default-servlet-handler>
     * 该注解会让springmvc 接收一个请求 并且该请求没有对应的@RequestMapping 时
     * 就会交给服务器的默认的servlet去访问
     *
     */

    /**
     * Ajax的原理与JavaScript的实现
     *
     */


    @RequestMapping(value="show36")
    public String test36() throws SQLException {
        hiveSer.query();
        System.out.println("自定义处理器正在执行！！");
        return "hello";
    }




    @RequestMapping(value="show37")
    public String test37() throws SQLException {
       User users = userService.queryById(1l);
        System.out.println(users);
        return "hello";
    }
    @RequestMapping(value="show38")
    public String test38() throws SQLException {
        int i = userService.queryTotalCount("stu");
        System.out.println(i);
        return "hello";
    }

    @RequestMapping("show39")
    public ModelAndView  test39(){
        ModelAndView modelAndView = new ModelAndView("Pages");
        int totalCount = userService.queryTotalCount("stu");
        pages.setTotalCount(totalCount);
        int totalPage = pages.getTotalCount()%pages.getPageSize() == 0?pages.getTotalCount()/pages.getPageSize() : pages.getTotalCount()/pages.getPageSize() +1;
        pages.setTotalPage(totalPage);
        System.out.println(pages);
        List<stu> stus = userService.queryPagesListService(pages.getCurrentPage(), pages.getPageSize());
        pages.setList(stus);
        modelAndView.addObject("p",pages);
        return modelAndView;
    }

    @RequestMapping("show40")
    public ModelAndView test40(@RequestParam("currentPage")String currentPage){
        ModelAndView modelAndView = new ModelAndView("Pages");
        int totalCount = userService.queryTotalCount("stu");
        pages.setCurrentPage(Integer.parseInt(currentPage));
        pages.setTotalCount(totalCount);
        int totalPage = pages.getTotalCount()%pages.getPageSize() == 0?pages.getTotalCount()/pages.getPageSize() : pages.getTotalCount()/pages.getPageSize() +1;
        pages.setTotalPage(totalPage);
        List<stu> stus = userService.queryPagesListService(pages.getCurrentPage(), pages.getPageSize());
        pages.setList(stus);
        System.out.println(pages);
        modelAndView.addObject("p",pages);
        return modelAndView;
    }
}
