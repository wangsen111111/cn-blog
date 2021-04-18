package org.example.util;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

/**
 * 数据库连接的单元测试
 */
public class DBUtilTest {

    @Test
    public void testGetConnection(){
        Connection connection = DBUtil.getConnection();
        Assert.assertNotNull(connection);

    }
}
