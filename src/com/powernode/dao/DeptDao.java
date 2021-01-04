package com.powernode.dao;

import com.powernode.pojo.Dept;
import com.powernode.pojo.Emp;
import com.powernode.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DeptDao {
    public List<Dept> queryAllDept() {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement statement = null;
        ResultSet res = null;
        List<Dept> list = new ArrayList<>();
        String sql = "SELECT * FROM `Dept`";
        try {
            statement = conn.prepareStatement(sql);
            res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("deptid");
                String name = res.getString("deptname");
                Dept dept = new Dept();
                dept.setDeptID(id);
                dept.setDeptName(name);
                list.add(dept);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(res, statement, conn);
        }
        return list;
    }
}
