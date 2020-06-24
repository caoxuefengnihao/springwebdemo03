package cn.service;

import cn.dao.IAccountDao;
import cn.pojo.Account;
import cn.pojo.user;

public interface IAccountService {
    void set(IAccountDao accountDao);
    void saveAccount(Account account);
    user queryLogin(user u);
}
