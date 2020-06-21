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
         */
      /*  ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        IAccountService accountService =(IAccountService) ac.getBean("accountService");
        accountService.saveAccount();*/

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        Account pojo = (Account)annotationConfigApplicationContext.getBean("pojo");
        IAccountService accountService = (IAccountServiceimpl)annotationConfigApplicationContext.getBean("accountService");
        accountService.saveAccount(pojo);

    }
}
