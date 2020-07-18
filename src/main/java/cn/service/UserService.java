package cn.service;

import cn.JavaBean.User;
import cn.pojo.stu;

import java.util.List;

public interface UserService {


    User queryById(Long id);
    int queryTotalCount(String tableName);
    List<stu> queryPagesListService(int currentPage,int pageSize);
}
