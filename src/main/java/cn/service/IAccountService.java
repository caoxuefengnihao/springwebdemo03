package cn.service;

import cn.dao.IAccountDao;
import cn.pojo.Account;

public interface IAccountService {
    void set(IAccountDao accountDao);
    void saveAccount(Account account);
}
