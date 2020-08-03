package cn.config;


import cn.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.springframework.core.io.Resource;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan("cn")
@Import({JdbcConfiguration.class,HiveJbdcConfig.class})
@EnableAspectJAutoProxy//开启AOP的注解扫描
@PropertySource({"classpath:mail.properties"})
@EnableScheduling//开启定时任务注解
public class SpringConfiguration {

    @Value("${mail.smtp.username}")
    String username;
    @Value("${mail.smtp.password}")
    String password;
    @Value("${mail.smtp.host}")
    String host;
    @Value("${mail.smtp.defaultEncoding}")
    String defaultEncoding;
    @Value("${mail.smtp.auth}")
    String auth;
    @Value("${mail.smtp.ssl.enable}")
    String sslEnable;
    /**
     * 在纯注解开发中 SqlSessionFactoryBean 这个类就相当于 mybatis的全局配置文件 在这里 配置一些mybatis 全局的一些配置 如给设置别名什么的
     * 通过SqlSessionFactoryBean初始化SqlSessionFactor
     * 在运行时 SqlSessionFactoryBean 会自动的通过 getObject方法 来获取 SQLSessionFactory 所以我们通过以下配置 最终获取到的是 SqlSessionFactory
     *
     * 我们在进行测试的时候还是需要自己从容器中获取SQLSessionFactory 然后通过SqlSessionFactory 来获取
     * SqlSession 最后在通过SQLSession 获取UserMapper 这样操作还是有点麻烦
     *
     * 试想 如果能将UserMapper 装配到spring中去 那么我们是不是可以直接从容器中获取UserMapper 并进行操作了呢
     * 如果SqlSessionFactory 能够交给spring管理 那么Map普洱接口行不行呢
     * Spring 提供了一个叫做MapperFactoryBean 专门用来管理Mapper 其实底层还是通过 SQLSession.getMapper(xx.class) 来获取一个 mapper的代理对象
     * 通过MapperFactoryBean 将UserMapper装配到spring容器中
     *
     * 实际上还是通过SQLSessionFactory 来获取session 最后获取 UserMapper
     *
     * @return SqlSessionFactoryBean
     *
     */
    @Bean("SqlSessionFactoryBean")
    public SqlSessionFactoryBean createSqlSessionFactory(@Qualifier("dataSource")DataSource ds){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(ds);
        Resource[] resources = {new ClassPathResource("UserDaoMapper.xml"),new ClassPathResource("UserMapper.xml")};
        sqlSessionFactoryBean.setMapperLocations(resources);
        return sqlSessionFactoryBean;
    }

    /**
     *
     * 整合Mapper动态代理类 配置单个mapper
     * 获取MapperFactoryBean
     *
     * 缺陷 如果整个工程中有多个mapper接口 那么如果每一个接口都配置一个MapperFactoryBean 显然太麻烦 不太合理 因此在spring和 mybatis 的整合
     * 包中还提供了一个MapperScannerConfigurer对象 该对象中提供了 通过配置包路径来扫描所有mapper接口的方式
     * 这个可以在配置类上 加 MapperScan注解来配置
     *
     * 这里有个非常重要的注解 @MapperScan("org.rockcode.mappers")，相当于里面
     * <mybatis:scan base-package="org.rockcode.mappers" />或者
     * <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
     *   <property name="basePackage" value="org.rockcode.mappers" />
     * </bean>
     *
     * @return
     * @throws Exception
     */
    @Bean("MapperFactoryBean")
    public MapperFactoryBean createMapperFactoryBean(@Qualifier("SqlSessionFactoryBean") SqlSessionFactoryBean s) throws Exception {
        MapperFactoryBean<UserMapper> userMapperMapperFactoryBean = new MapperFactoryBean<>();
        userMapperMapperFactoryBean.setMapperInterface(UserMapper.class);
        userMapperMapperFactoryBean.setSqlSessionFactory(s.getObject());
        return userMapperMapperFactoryBean;
    }



    @Bean("SqlSessionFactory1")
    public SqlSession createSqlSessionHive() throws Exception {

        //获取全局配置文件输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        //加载全局配置文件
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream,"work");
        //获取SQLSession
        SqlSession sqlSession = build.openSession();
        return sqlSession;
    }

    @Bean("SqlSessionFactory")
    public SqlSession createSqlSession() throws Exception {

        //获取全局配置文件输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        //加载全局配置文件
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //获取SQLSession
        SqlSession sqlSession = build.openSession();
        return sqlSession;
    }


    @Bean("JavaMailSenderImpl")
    public JavaMailSenderImpl createMail(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setDefaultEncoding(defaultEncoding);
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth",auth);
        properties.setProperty("mail.smtp.ssl.enable",sslEnable);
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    /**
     * spring 整合 Thymeleaf
     *
     */
    @Bean("SpringResourceTemplateResolver")
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver  = new SpringResourceTemplateResolver();
        templateResolver.setCacheable(false);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("utf-8");
        return templateResolver;
    }

    @Bean("SpringTemplateEngine")
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }



}
