package cn.dao.impl;

import cn.dao.IAccountDao;
import cn.pojo.Account;
import cn.pojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component(value = "accountDao")
public class IAccountDaoimpl implements IAccountDao {
    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Override
    public void saveAccount(Account account) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "insert into mima values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,account.getName());
            preparedStatement.setString(2,account.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("保存了账户");
    }

    @Override
    public user queryLogin(user u) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select * from mima where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,u.getUsername());
            preparedStatement.setString(2,u.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String string1 = resultSet.getString(1);
                String string2 = resultSet.getString(2);
                return new user(string1,string2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("保存了账户");
        return null;
    }
}
