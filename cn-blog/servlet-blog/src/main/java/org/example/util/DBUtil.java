package org.example.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.example.exception.AppException;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    //把数据库的密码改成我DB的密码
    private static final String URL = "jdbc:mysql://localhost:3306/servlet_blog?user=root&password=&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    //引用DataSource获取数据库的连接对象,<jdbc>DataSource接口，它负责建立与数据库的连接
    private static final DataSource dataSource = new MysqlDataSource();

    /**
     * 工具类提供数据库JDBC操作
     * 不足：1.static代码块出现错误，NoClassDefFoundError表示类可以找到，但是类加载失败，无法运行
     *        ClassNotFoundException：找不到类
     *      2.利用多线程之后，可能会采取双重校验锁的单例模式来创建DataSource
     *      3.工具类设计上不是最优，数据库框架ORM框架-Mybatis-，都是模板模式设计的
     */
    static {
        ((MysqlDataSource)dataSource).setUrl(URL);
    }
    //连接对象
    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException throwables) {

            // 数据库连接失败，抛自定义异常
            throw new AppException("DB001","获取数据库连接失败",throwables);
        }
    }

    public static void close(Connection c, Statement s){
        close(c, s,null);
    }
    //数据库Connection、Statement、ResultSet这些资源需要我们手动管理
    public static void close(Connection c, Statement s, ResultSet r){
        try {
            //反向释放
            if(r!=null)
                r.close();
            if(s!=null)
                s.close();
            if(c!=null)
                c.close();
        }catch (SQLException e){
            throw new AppException("DB002","数据库释放资源出错",e);
        }
    }
}
