package cn.service.impl;

import cn.dao.IAccountDao;
import cn.pojo.Account;
import cn.service.IAccountService;

public class IAccountServiceimpl2 implements IAccountService {

    private String name;
    private String age;
    private IAccountDao accountDao;
    public IAccountServiceimpl2(String name, String age, IAccountDao accountDao) {
        this.name = name;
        this.age = age;
        this.accountDao = accountDao;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public IAccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void set(IAccountDao accountDao) {

    }

    @Override
    public void saveAccount(Account account) {
        accountDao.saveAccount(account);
    }
}
