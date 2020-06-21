package cn.service.impl;

import cn.dao.IAccountDao;
import cn.pojo.Account;
import cn.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component(value = "accountService")
public class IAccountServiceimpl implements IAccountService {

    @Autowired
    @Qualifier("accountDao")
    public IAccountDao iAccountDao ;

    public IAccountDao getiAccountDao() {
        return iAccountDao;
    }

    public void set(IAccountDao iAccountDao) {
        this.iAccountDao = iAccountDao;
    }

    @Override
    public void saveAccount(Account account) {
        iAccountDao.saveAccount(account);
    }
}
