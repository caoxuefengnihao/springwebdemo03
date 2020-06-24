package cn.handler;


import cn.pojo.user;
import cn.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class spring {


    @Autowired
    @Qualifier("helloServiceImpl")
    IHelloService iHelloService;

    /**
     * @RequestMapping("/show1.do"): 使得请求的url可以映射到指定的目标方法上
     * @return
     */
    @RequestMapping("/show1.do")
    public ModelAndView test1(){
        ModelAndView mv = new ModelAndView();
        //设置要显示的页面：视图
        mv.setViewName("/WEB-INF/views/hello.jsp");
        //添加要显示的数据:数据是存在request域中
        mv.addObject("msg","这是springmvc的第一个程序！");
        return mv;
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
     * 通过@RequestParam("username")来获取前段form表单传过来的参数
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
     *  以上的代码实现了请求转发
     *  现在我们要想的是怎么将controler 处理的结果返回给前段页面  我们应该考虑两个方面 视图页面（v）与数据（m）
     *  那么这两个数据怎么保存呢  mvc已经给我们提供了  如果跳转是需要带数据  v,m可以使用以下方式
     *  ModelAndView ModelMap Map Model  ----数据放在了request的作用域当中
     *
     *
     */
    @RequestMapping(value="show27")
    public String test22(Model model) {
        List<user> list = new ArrayList<user>();
        for(int i = 0;i< 20;i++) {
            user user = new user();
            user.setUsername("zhangsan"+i);
            System.out.println(user);
        }
        model.addAttribute("users",list);
        return "/userList.jsp";
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
}
