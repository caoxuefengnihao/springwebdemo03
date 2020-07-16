package cn.mapper;

import cn.JavaBean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    public User queryUserById(Long id);

    /**
     * 查询所有用户
     * @return
     */
    public List<User> queryUserList();

    /**
     * 新增用户
     * @param user
     */
    public void insertUser(User user);

    /**
     * 更新用户信息
     * @param user
     */
    public void updateUser(User user);

    /**
     * 根据id删除用户信息
     * @param id
     */
    public void deleteUserById(Long id);

    /**
     * 通过用户名进行模糊查询,如果没有用户名，查询所有男性用户
     * @param userName
     * @return
     */
    List<User> queryUsersLikeUserName(@Param("userName")String userName);

    List<User> queryUserListByUserNameOrAge(@Param("userName")String userName,@Param("age")Integer age);
    List<User> queryUserListByUserNameandAge(@Param("userName")String userName,@Param("age")Integer age);

}
