package cn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.*;
@Configuration
@PropertySource({"classpath:hive.properties"})
public class HiveJbdcConfig {

    @Value("${hivedriverClassName}")
    String driverClassName;
    @Value("${hiveurl}")
    String url;
    @Value("${hiveusername}")
    String username;
    @Value("${hivepassword}")
    String password;


    @Bean("getConnect")
    public Connection getConnect(){
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }





}





