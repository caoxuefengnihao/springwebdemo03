package cn.dao.impl;

import cn.dao.IAccountDao;
import cn.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
