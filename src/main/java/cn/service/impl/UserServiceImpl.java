package cn.service.impl;

import cn.JavaBean.User;
import cn.mapper.UserMapper;
import cn.pojo.stu;
import cn.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("SqlSessionFactory")
    SqlSession sqlSession;

    @Override
    public User queryById(Long id) {

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user1 = mapper.queryUserById(id);
        System.out.println(user1);
        return user1;
    }

    @Override
    public int queryTotalCount(String tableName) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.queryTotalCount(tableName);
        return i;
    }

    @Override
    public List<stu> queryPagesListService(int currentPage, int pageSize) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<stu> stus = mapper.queryPagesList(currentPage*pageSize, pageSize);
        return stus;
    }
}
