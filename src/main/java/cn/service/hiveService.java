package cn.service;

import cn.JavaBean.HiveBean;

import java.sql.SQLException;
import java.util.List;

public interface hiveService {
    void query() throws SQLException;
    List<HiveBean> queryHive();
}
