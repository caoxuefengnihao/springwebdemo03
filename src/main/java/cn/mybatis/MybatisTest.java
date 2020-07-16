package cn.mybatis;

import cn.JavaBean.User;
import cn.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    public static void main(String[] args) throws IOException {
        //获取全局配置文件输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        //加载全局配置文件
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //获取SQLSession
        SqlSession sqlSession = build.openSession();
        User user = sqlSession.selectOne("UserMapper.queryUserById", 1L);
        System.out.println(user);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user1 = mapper.queryUserById(1l);
        System.out.println(user1);

        List<User> zhang = mapper.queryUsersLikeUserName("zhang");
        for (User user2 : zhang) {
            System.out.println(user2);
        }

        List<User> zhang1 = mapper.queryUserListByUserNameOrAge("zhang", 20);
        for (User user2 : zhang1) {
            System.out.println(user2);
        }
        List<User> zhang2 = mapper.queryUserListByUserNameandAge("zhang", 20);
        for (User user3 : zhang2) {
            System.out.println(user3);
        }

        /**
         * 出现的问题  userName=null
         * 解决Username为null
         * 查询数据的时候 查不到userName的信息 原因 数据库的字段名是user_name 但是pojo的属性名称是 userName
         * 两端不一致 造成mybatis无法填充对应的字段信息
         * 解决方案 在sql语句中使用别名
         *
         * 所以 mybatis的使用步骤是
         * 配置文件
         * 1 配置全局的配置文件
         *      设置环境（事务，数据源）
         *      管理映射文件
         * 2 配置映射文件
         *      Mapper标签 用来配置不同的statement
         *      Namespace属性 用来配置命名空间 来区分不同的映射文件
         *      编写的crud的statement select update insert delete
         *      select 专门用于查询的statement 可以编写查询语句
         *      id属性 该statement的唯一标识 一般用来被引用
         *      resultType 结果集类型
         *      paramterType 参数的类型 可以省略
         *
         * 事务问题 如果在进行增删改的操作是 发现没有任何作用 那是因为增删改操作需要事务的提交
         * 我们可以在执行SQLSession的查询方式后手动添加事务提交sqlSession.commit();//提交事务 也可以
         * 在创建SqlSession的时候设置自动提交
         *
         * Mapper动态代理方式
         * 考虑curd中的问题
         * 1 接口 到  实现类
         * 2每次curd实现类都需要传递SQLSession
         *
         * Mapper接口的动态代理实现需要遵循以下规范
         *  1 映射文件中的命名空间与Mapper接口的全路径一致
         *  2 映射文件中的statement 的id 与Map普洱接口的方法名保持一致
         *  3 映射文件中的statement的ResultType 必须和Mapper 接口方法的返回类型 一致
         *  4 映射文件中的statement的parameterType必须和mapper接口方法的参数类型一致
         *
         * 采用动态代理之后 只剩下UserMapper接口 UserMapper.xml 映射文件以及UserMapper 接口的测试文件
         * 即可以少写一个 接口的实现类 成功
         *
         * parameterType传入参数
         * crud标签都有一个属性 parameterType statement通过他指定接收的参数类型
         * 接收的参数方式有两种
         * 1 #{} 预编译
         * 2 ${} 非预编译 直接的sql拼接 不能防止注入
         *
         * 可以用@Param 来指定参数名称  就是 #{"这里的参数名称就是@param指定的"}
         * 参数的类型有三种
         * 1 基本数据类型
         * 2 HashMap 使用方式与pojo类似
         * 3 pojo自定义包装类型
         * resultMap 是mybatis中最重要最强大的元素 使用ResultMap可以解决两大问题
         * POJO 属性名和表结构字段名不一致问题
         * 完成高级查询 比如说 一对一  一对多 多对多
         *  解决列名和实体类中的属性名不一致
         *  看之前的例子  查询数据的时候 查不到userName的信息 原因 数据库的字段名是user_name 而pojo中的属性名是
         *  userName 两端不一致 造成mybatis 无法填充对应的字段信息
         *  解决办法 在sql中使用别名/resultMap自定义映射
         *  resultMap标签的作用 自定义结果集 自行设置结果集的封装方式
         *      id属性 resultMap标签的唯一标识 不能重复 一般是用来被引用的
         *      type属性 结果集的封装类型
         *      autoMapping属性 操作表单时 不配置默认为true，如果pojo对象中的属性名称和表中字段名称相同 则自动映射
         *      关于接口的返回值类型 阈值匹配规则
         *      如果接口的返回值类型 是list 那么在select的resultType只写list的泛型即可
         *      如果接口的返回值类型 是map
         *      分为两种情况
         *          如果返回值是一条数据 那么则Map<String, Object>中，String对应着字段名，Object对应着字段 select 中的resultType 写成map
         *          如果返回值是多条数据 那么通过@MapKey定义返回Map集合的key是哪个字段的值，Map集合的Value则为每行数据的值 select 中的resultType 写成泛型就行
         * 动态sql 常用的动态sql if choose when otherwise  where set  foreach
         * if标签  判断语句 用来进行逻辑判断的 test属性 用来编写表达式
         * where 可以自动将动态sql中多出来的一个and或or去除
         * set 可以将动态sql最后多余的 , 去除
         * foreach
         * 	foreach标签：遍历集合或者数组
         * 	collection属性：接收的集合或者数组参数
         * 	item属性：集合或者数组参数中的每一个元素
         * 	separator属性：标签分隔符
         * 	open属性：以什么开始
         * 	close属性：以什么结束
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         */


    }
}
