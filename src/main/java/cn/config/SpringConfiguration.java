package cn.config;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan("cn")
@Import({JdbcConfiguration.class,HiveJbdcConfig.class})
@EnableAspectJAutoProxy//开启AOP的注解扫描
@PropertySource({"classpath:mail.properties"})
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
     * @return
     */

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
