package cn.web;


import cn.config.SpringConfiguration;
import cn.pojo.Account;
import cn.service.IAccountService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

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
         * 也可以用在@Component注解的类里。添加的bean的id为方法名
         * 默认情况下bean的名称和方法名称相同，你也可以使用name属性来指定
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

        /**
         * 实现分页的相关知识
         *
         * 我们需要五个变量
         * 1 数据总数 我们需要查询数据库  100  103  已经完成
         * 2 页面大小  也就是每个页面显示的条数  这个应该由用户自定义  20
         * 3 总页数
         *      分为两种情况
         *          总页数 = 100/20 = 数据总数/页面大小
         *          总页数 = 103/20 = 数据总数/页面大小 + 1
         *      所以
         *      总页数 = 数据总数%页面大小 == 0 ? 数据总数/页面大小 : 数据总数/页面大小 + 1
         *
         * 4 当前页 用户自定义的
         * 5 当前页的对象集合 查数据库 分页sql
         * mysql的分页sql
         *  select * from table limit a,b
         *
         */


        /**
         * Thymeleaf 相关简介  他可以完全替代jsp 相较于其他的模板引擎 他有如下山歌极吸引人的特点
         * 1 Thymeleaf 在有网络和无网络的情况下 都可以运行
         * 2 Thymeleaf 提供了Spring 标准语言和一个与springmvc完美集成的可选模块  可以快速的实现表单绑定 属性编辑器 国际化等功能
         *
         * Thymeleaf 与 spring的相关集成
         * Thymeleaf 提供了一组spring 集成 可以使用springmvc完全替代 jsp的功能
         *
         *
         * spring的 相关代码
         * @Bean
         * public SpringResourceTemplateResolver templateResolver(){
         *     // SpringResourceTemplateResolver automatically integrates with Spring's own
         *     // resource resolution infrastructure, which is highly recommended.
         *     SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
         *     templateResolver.setApplicationContext(this.applicationContext);
         *     templateResolver.setPrefix("/WEB-INF/templates/");
         *     templateResolver.setSuffix(".html");
         *     // HTML is the default value, added here for the sake of clarity.
         *     templateResolver.setTemplateMode(TemplateMode.HTML);
         *     // Template cache is true by default. Set to false if you want
         *     // templates to be automatically updated when modified.
         *     templateResolver.setCacheable(true);
         *     return templateResolver;
         * }
         *
         * @Bean
         * public SpringTemplateEngine templateEngine(){
         *     // SpringTemplateEngine automatically applies SpringStandardDialect and
         *     // enables Spring's own MessageSource message resolution mechanisms.
         *     SpringTemplateEngine templateEngine = new SpringTemplateEngine();
         *     templateEngine.setTemplateResolver(templateResolver());
         *     // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
         *     // speed up execution in most scenarios, but might be incompatible
         *     // with specific cases when expressions in one template are reused
         *     // across different data types, so this flag is "false" by default
         *     // for safer backwards compatibility.
         *     templateEngine.setEnableSpringELCompiler(true);
         *     return templateEngine;
         * }
         *
         */

        /**
         * 邮件组件的相关开发
         * 在编写邮件相关开发之前 我们先搞清楚电子邮件是如何在网上运作的 假设你要给远方的一个人 发邮件 步骤
         * 写好信装进信封 --> 写好地址 贴上邮票 --> 找邮局 发信  --> 然后信件就会从就近的小邮局转运到大邮局 在从大邮局发送到其他城市 最后发送到目的地的某个邮局 期间邮件的发送路线你不需要关心
         * 然后信件就会投递到你朋友的邮箱里 之后就可以读取邮件了
         *
         * 电子邮箱 跟上述步骤一样
         * 假设我们自己的电子邮箱是 me@163.com 对方的电子邮箱地址是friend@163.com  现在我们用Outlook 或者 Foxmail 之类的
         * 软件写好邮件 填上对方的邮件地址点发送  电子邮件就发出去了  这些电子邮件软件被称为 MUA mail user agent 邮件用户代理
         *
         * Email 从MUA 发出去之后 不是直接到达对方的电脑 而是发送到 MTA mailTransferAgent--邮件传输代理 特就是那些 Email 服务提供商
         * 比如 网易 新浪  中间的过程可能还会经过别的 MTA  但是我们不关心具体的路线 我们只关心速度
         *
         * 网易（由于我们发送的是新浪的后缀名）最终会把Email 投递到邮件的最终目的地 MDA Mail Delivery Agent -- 邮件投递代理
         * Email 到达 MDA 后 就静静的躺在新浪的某个服务器上 存放在某个文件或特殊的数据库里 我们将这个长期保存邮件的地方
         * 称之为 电子邮箱
         *
         * Email 不会直接到达对方的电脑 因为对方的电脑不一定开机 开机也不一定联网 对方要取到电子邮件 必须通过MUA 从 MDA 上
         * 把邮件取到自己的电脑上
         * 所以 一封电子邮件的旅程就是：
         * 发件人 --> MUA --> MTA（若干个） -- MDA <-- MUA 收件人
         * 所以 有了上述的基本概念 要编写程序来发送和接收邮件 本质上就是
         *      1 编写 MUA 把邮件 发送到 MTA
         *      2 编写 MUA 从MDA 上收邮件
         * 那么 发邮件是 MUA 和 MTA 使用的协议就是 SMTP Simple Mail Transfer Protocol 后面的MTA 到另一个MTA
         * 也是用SMTP协议
         * 那么收邮件时 MUA 和MDA 使用的协议有两种 POP IMAP
         * 最后特别注意，目前大多数邮件服务商都需要手动打开SMTP发信和POP收信的功能，否则只允许在网页登录
         */

        /*  ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        IAccountService accountService =(IAccountService) ac.getBean("accountService");
        accountService.saveAccount();*/

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        Account pojo = (Account)annotationConfigApplicationContext.getBean("pojo");
        IAccountService accountService = (IAccountService)annotationConfigApplicationContext.getBean("accountService");
        accountService.saveAccount(pojo);



        /**
         * JavaMailSenderImpl支持MimeMessages和SimpleMailMessages。
         * MimeMessages为复杂邮件模板，支持文本、附件、html、图片等。
         * SimpleMailMessages实现了MimeMessageHelper，为普通邮件模板，支持文本
         */
        JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)annotationConfigApplicationContext.getBean("JavaMailSenderImpl");
        SimpleMailMessage message = new SimpleMailMessage(); // 创建消息对象
        message.setSubject("标题"); // 标题
        message.setText("正文"); // 只支持文本, 不支持html
        message.setTo("1394269623@qq.com"); // 收件人
        message.setFrom("15904920415@163.com"); // 发件人
        javaMailSenderImpl.send(message); // 发送
    }
}
