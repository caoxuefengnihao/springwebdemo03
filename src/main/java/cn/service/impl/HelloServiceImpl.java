package cn.service.impl;

import cn.dao.IHelloDao;
import cn.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("helloServiceImpl")
public class HelloServiceImpl implements IHelloService {

    @Autowired
    @Qualifier("helloDaoImpl")
    private IHelloDao helloDao;

    public void setHelloDao(IHelloDao helloDao) {
        this.helloDao = helloDao;
    }

    @Override
    public void sayHello() {
        System.out.println("业务层的sayHello");
        helloDao.sayHello();
    }
}
