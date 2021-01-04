package com.powernode.dao;

import com.powernode.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    public boolean login(String username, String password) {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement statement = null;
        ResultSet res = null;
        boolean flag = false;
        String sql = "SELECT * FROM User WHERE `username` = ? AND `password` = MD5(?)";
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            res = statement.executeQuery();
            if (res.next()) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(res, statement, conn);
        }
        return flag;
    }
}
