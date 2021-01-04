package com.powernode.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;

public class JDBCUtils {
    private static final ComboPooledDataSource dataSource;

    static {
        dataSource = new ComboPooledDataSource();
    }

    public static Connection getConnection() {
        Connection conn = null;
        if (dataSource != null) {
            try {
                conn = dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void close(Statement statement, Connection conn) {
        close(null, statement, conn);
    }

    public static void close(ResultSet res, Statement statement, Connection conn) {
        if (res != null) {
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
