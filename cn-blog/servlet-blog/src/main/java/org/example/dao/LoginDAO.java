package org.example.dao;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class LoginDAO {

    public static User query(String username) {
        //创建连接对象
        Connection connection = null;
        //创建执行sql语句对象
        PreparedStatement ps = null;
        //创建返回结果集对象
        ResultSet rs = null;
        //定义返回的User对象
        User user = null;
        try {
            /**
             * jdbc操作：
             * 1，获取连接
             * 2,根据连接创建操作命令对象
             * 3，执行sql
             * 4，查询操作要处理结果集
             * 5，释放资源
             */
            connection = DBUtil.getConnection();
            String sql = "select id, username, password," +
                    " nickname, sex, birthday, head from user" +
                    " where username=?";
            ps = connection.prepareStatement(sql);
            //设置占位符
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                user = new User();
                //设置User的值
                user.setId(rs.getInt("id"));
                user.setUsername(username);
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setSex(rs.getBoolean("sex"));
                //关于日期的使用，java中一般使用java,util.Date（年月日时分秒）
                //rs.getDate()返回值是java.sql.Date(时分秒)
                //rs.getTimestamp()返回值是java.sql.Timestamp（年月日时分秒）
                java.sql.Date birthday = rs.getDate("birthday");
                if (birthday != null)
                    user.setBirthday(new Date(birthday.getTime()));
                user.setHead(rs.getString("head"));
            }
            return user;
        } catch (Exception e) {
            throw new AppException("LOG001", "查询用户操作出错", e);
        } finally {
            DBUtil.close(connection, ps, rs);
        }
    }
}
