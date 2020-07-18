package cn.web;


import cn.config.SpringConfiguration;
import cn.pojo.Account;
import cn.service.IAccountService;
import cn.service.impl.IAccountServiceimpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Client {

    public static void main(String[] args) {
        /**
         * 通过注解的方式来实现ioc与依赖注入
         * @Component 注解装配bean
         *  作用：在类上使用该注解 把资源让spring来管理 相当于在xml中配置一个bean
         *  其中的属性 value  指定bean的id 如果不指定value属性 默认bean的id是当前类的类名 首字母小写
         *  相当于<bean id = "" class = ""></bean>
         * @Controller  一般用于表现层的注解
         * @Service 一般用于业务层的注解
         * @Repository  一般用于持久成的注解
         *  上面这三个注解都是衍生注解 他们的作用及属性都是一模一样的 只不过他们提供了更加明确的
         *  语义化
         *
         * @Scope
         *
         * 依赖注入相关的
         * @Autowired 按照类型注入 如果有多个类型匹配时 将对象的变量名作为id 去容器中查找并注入 找不到就报错
         *  作用：自动按照类型注入 当使用注解属性时 set方法可以省略 他只能注入其他类型的bean 当有多个
         *  类型匹配时 使用要注入的对象的变量名称作为bean的id
         * @Qualifier
         *  作用：在自动按照类型注入的基础之上 在按照bean的id注入 他在给字段注入时不能
         *  单独使用 必须和@Autowire 一起使用 但是给方法参数注入时可以独立使用
         * @Resource
         * 作用：直接按照bean的id进行注入
         * 属性：name 指定bean的id
         * @Value("${jdbc.url}") @Value(“${}”)和@Value(“#{}”) 固定格式
         *  作用：注入基本类型数据和string类型
         *
         *
         * 这些是用来实现纯注解开发的 相关的注解 摆脱 xml文件
         * @Configuration
         *  作用：由于指定当前类是一个spring配置类 当创建容器是会从该类上加载注解
         *  在获取容器时需要使用AnnotationApplicationContext(有@Configuration注解的类.class)
         *  注意 我们已经把配置文件用类来代替了 但是如何配置创建容器是要扫描的包呢 看下面的注解
         * @ComponentScan("要扫描的包路径")
         *  作用：用于指定spring在初始化容器是要扫描的包 作用和在spring的xml配置文件中的 <context:component-scan base-package="cn.itcast"></context:component-scan>一样
         * @Bean(name = "给当前的@Bean注解方法创建的对象指定一个名称 即bean的id")
         * 就是有一个方法 这个注解就是给这个方法创建一个对象
         * @PropertySource
         * 作用：用于加载.properties文件中的配置 例如我们配置数据源时，可以把链接数据库的信息写到properties文件
         * 中 就可以使用此注解指定properties配指文件的位置
         * @Import
         *  作用：用于导入其他配置类 在引用其他配置类上可以不再使用@Configuration注解
         *  value[] 由于指定其他配置类的字节码
         *
         *
         * AOP概述 什么是AOP 面向切面编程
         * 简单的来说它就是把我们程序重复的代码抽取出来 在需要执行的时候 使用动态代理 在不改源码的基础上 对我们已有的方法进行增强
         * AOP的作用和优势
         * 作用 在程序运行期间 不修改源码对已有的方法进行增强
         * 优势 减少重复代码 提高开发效率 方便维护
         *
         * AOP中的相关术语
         * Joinpoint（连接点） 所谓的连接点是指那些被拦截到的点 在spring中 这些点指的是方法 欣慰spring只支持方法类型的连接点
         * Pointcut（切入点） 所谓的切入点是指我们要对那些Joinpoint进行拦截的定义
         * Advice（通知/增强） 所谓的通知是指拦截到Joinpoint之后所要做的事情就是通知
         *  通知的类型 前置通知 后置通知 异常通知 最终通知 环绕通知
         * Target 目标对象 代理的目标对象
         * Weaving 织入
         *  是指把增强应用到目标对象来创建新的代理对象的过程
         * Proxy 代理 一个类被AOP织入增强后 就产生一个结果代理类
         * Aspect 切面 是切入点和通知的结合
         *
         * AspectJ切入点表达式总结
         * execution  匹配方法的执行（常用）
         * 语法表达式 execution([方法修饰符 例如public] 返回值类型 包名.类名.方法名(参数))
         * 全匹配方式
         * execution(public void cn.service.impl.IAccountServiceimpl.saveAccount(..))
         * 访问修饰符可以省略
         * execution(void cn.service.impl.IAccountServiceimpl.saveAccount(..))
         * 返回值类型可以使用* 表示返回任意类型
         * execution(* cn.service.impl.IAccountServiceimpl.saveAccount(..))
         * 包名可以用* 表示任意包 但是有几级包 就要写几个*
         * execution(* cn.service.impl.IAccountServiceimpl.saveAccount(..)) 有三级包 要写三个*
         * execution(* *.*.*.IAccountServiceimpl.saveAccount(..))
         * 使用 .. 来表示当前的包及其子包
         *
         * 类名可以用 * 表示 任意类
         * execution(* *.*.*.*.saveAccount(..))
         * 方法名也可以用 * 表示 表示任意方法
         * execution(* *.*.*.*.*(..))
         * 参数列表也可以用 * 表示 表示任意参数 但是必须有参数
         * execution(* *.*.*.*.*(*))
         * 参数列表也可以用 .. 表示 表示任意参数 有无参数都行
         * execution(* *.*.*.*.*(..))
         * 全通配方式
         * * *..*.*(..)
         * 注解实现AOP 相关注解解释
         * 1 在通知类上使用@Aspect 注解声明为切面类
         * @Before 作用 把当前方法看成是前置通知 属性 value 用于指定切入点表达式
         * 还可以指定切入点表达式的引用
         * @AfterReturning 把当前方法看成是后置通知
         * 属性 value 用于指定切入点表达式 还可以指定切入点表达式的引用
         * @AfterThrowing 帮当前方法看成异常通知
         * 属性 value 用于指定切入点表达式 还可以指定切入点表达式的引用
         * @After 把当前方法看成是最终通知
         * 属性 value 用于指定切入点表达式 还可以指定切入点表达式的引用
         * 重点 环绕通知的注解配置
         * @Around 把当前方法看成是环绕通知
         * 属性 value 用于指定切入点表达式 还可以指定切入点表达式的引用
         *
         * spring框架为我们提供了一个接口 该接口可以作为环绕通知的方法参数来使用
         * ProceedingJoinPoint 当环绕通知执行是 spring框架会为我们注入该接口的实现类
         * 他有一个方法 proceed() 就相当于invoke 明确业务层方法调用
         * spring的环绕通知
         * 他是spring为我们提供的一种可以在代码中手动控制增强方法何时执行的方式
         *
         * 切入点表达式注解
         * @Pointcut 作用指定切入点表达式 属性 value 指定表达式的内容
         *
         *
         *
         * 看下面的例子
         * 在业务层方法执行的前后 加入日志的输出
         */
        /**
         * spring 与 mybatis的整合
         * mybatis可以操作数据库 其实就是通过SQLSessionFactory来获取 SQLSession 然后通过SQLSession
         * 来操作数据库 而之前的SQLSessionFactory都是我们手动初始化出来的 而所谓的spring整合mybatis其实就是将
         * SqlSessionFactory交给spring 的ioc 容器进行初始化
         *  mybatis 和 spring整合 包提供了一个SQLSessionFactoryBean 该对象是一个工厂 bean 实现了 FactoryBean
         *  可以通过getObject方法返回来一个SQLSessionFactory
         *
         *
         *
         *
         *
         *
         *
         *
         */








      /*  ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        IAccountService accountService =(IAccountService) ac.getBean("accountService");
        accountService.saveAccount();*/

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        Account pojo = (Account)annotationConfigApplicationContext.getBean("pojo");
        IAccountService accountService = (IAccountService)annotationConfigApplicationContext.getBean("accountService");
        accountService.saveAccount(pojo);
    }
}
