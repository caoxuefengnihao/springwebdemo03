package cn.service.impl;

import cn.JavaBean.HiveBean;
import cn.dao.impl.hiveDaoImpl;
import cn.mapper.UserMapper;
import cn.service.hiveService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component("hiveServiceImpl")
public class hiveServiceImpl implements hiveService {


    @Autowired
    @Qualifier("hiveDaoImpl")
    hiveDaoImpl hi;
    @Autowired
    @Qualifier("SqlSessionFactory1")
    SqlSession sqlSession;


    @Override
    public void query() throws SQLException {
        hi.query();
    }

    @Override
    public List<HiveBean> queryHive() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.queryHIve());
        return mapper.queryHIve();
    }
}
