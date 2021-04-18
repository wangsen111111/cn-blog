package org.example.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;

public class SingleDataSource {

    private static class Inner{
        private static final DataSource ds = new MysqlDataSource();
    }

    public static DataSource getDataSource(){
        return Inner.ds;
    }
}
