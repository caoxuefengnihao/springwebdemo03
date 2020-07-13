package cn.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("cn")
@Import({JdbcConfiguration.class,HiveJbdcConfig.class})

public class SpringConfiguration {



}
