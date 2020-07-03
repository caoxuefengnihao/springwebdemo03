package cn.dao;


import cn.pojo.Account;
import cn.pojo.user;

public interface IAccountDao {

    void saveAccount(Account account);
    user queryLogin(user u);
    int zuce(user u);
}
