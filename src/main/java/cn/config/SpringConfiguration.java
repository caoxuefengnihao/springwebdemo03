package cn.config;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.*;
import java.io.InputStream;

@Configuration
@ComponentScan("cn")
@Import({JdbcConfiguration.class,HiveJbdcConfig.class})
@EnableAspectJAutoProxy//开启AOP的注解扫描
public class SpringConfiguration {


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
}
