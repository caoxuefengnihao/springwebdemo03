package cn.dao.impl;

import cn.dao.hiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@Component("hiveDaoImpl")
public class hiveDaoImpl implements hiveDao {

    @Autowired
    @Qualifier("getConnect")
    Connection connection;


    @Override
    public void query() throws SQLException {
        String sql = "select * from test.test01";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));
        }

    }
}
