package cn.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("cn")
@Import({JdbcConfiguration.class,HiveJbdcConfig.class})
@EnableAspectJAutoProxy//开启AOP的注解扫描
public class SpringConfiguration {



}
