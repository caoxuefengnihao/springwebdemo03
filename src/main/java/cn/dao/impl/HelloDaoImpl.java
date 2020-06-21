package cn.dao.impl;

import cn.dao.IHelloDao;
import org.springframework.stereotype.Component;

@Component("helloDaoImpl")
public class HelloDaoImpl implements IHelloDao {
    
    public void sayHello() {
        System.out.println("持久层的sayHello");
    }
}
