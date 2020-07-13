package cn.service.impl;

import cn.dao.impl.hiveDaoImpl;
import cn.service.hiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component("hiveServiceImpl")
public class hiveServiceImpl implements hiveService {


    @Autowired
    @Qualifier("hiveDaoImpl")
    hiveDaoImpl hi;


    @Override
    public void query() throws SQLException {
        hi.query();
    }
}
