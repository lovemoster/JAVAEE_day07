package com.powernode.dao;

import com.powernode.pojo.Emp;
import com.powernode.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmpDao {
    public List<Emp> queryAllEmp() {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement statement = null;
        ResultSet res = null;
        List<Emp> list = new ArrayList<>();
        String sql = "SELECT * FROM `Emp`";
        try {
            statement = conn.prepareStatement(sql);
            res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                int age = res.getInt("age");
                String birthday = res.getString("birthday");
                int sex = res.getInt("sex");
                int deptid = res.getInt("deptid");
                Emp emp = new Emp();
                emp.setId(id);
                emp.setName(name);
                emp.setAge(age);
                emp.setDate(birthday);
                emp.setSex(sex);
                emp.setDeptID(deptid);
                list.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(res, statement, conn);
        }
        return list;
    }

    public void insertEmp(Emp emp) {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement statement = null;
        String sql = "INSERT INTO Emp(name,age,birthday,sex,deptid,filePath,fileHash) VALUES (?,?,?,?,?,?,?)";
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, emp.getName());
            statement.setInt(2, emp.getAge());
            statement.setString(3, emp.getDate());
            statement.setInt(4, emp.getSex());
            statement.setInt(5, emp.getDeptID());
            statement.setString(6, emp.getFilePath());
            statement.setString(7, emp.getFileHash());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(statement, conn);
        }
    }

    public void deleteEmpByID(int id) {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement statement = null;
        String sql = "DELETE FROM `Emp` WHERE id = ?";
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(statement, conn);
        }
    }

    public void editEmp(Emp emp) {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement statement = null;
        String sql = "UPDATE Emp SET name = ?, age = ?,birthday = ?,sex = ?, deptid = ? ,filePath = ?, fileHash = ? WHERE  id = ? ";
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, emp.getName());
            statement.setInt(2, emp.getAge());
            statement.setString(3, emp.getDate());
            statement.setInt(4, emp.getSex());
            statement.setInt(5, emp.getDeptID());
            statement.setString(6, emp.getFilePath());
            statement.setString(7, emp.getFileHash());
            statement.setInt(8, emp.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(statement, conn);
        }
    }

    public List<Emp> getData(int current, int limit) {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement statement = null;
        ResultSet res = null;
        String sql = "SELECT * FROM Emp LIMIT ?,?";
        List<Emp> list = new ArrayList<>();
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, (current - 1) * limit);
            statement.setInt(2, limit);
            res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                int age = res.getInt("age");
                String birthday = res.getString("birthday");
                int sex = res.getInt("sex");
                int deptid = res.getInt("deptid");
                String filePath = res.getString("filePath");
                String fileHash = res.getString("fileHash");
                Emp emp = new Emp();
                emp.setId(id);
                emp.setName(name);
                emp.setAge(age);
                emp.setDate(birthday);
                emp.setSex(sex);
                emp.setDeptID(deptid);
                emp.setFilePath(filePath);
                emp.setFileHash(fileHash);
                list.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(res, statement, conn);
        }
        return list;
    }


    public List<Emp> getDataLike(int current, int limit, String ename, int eage) {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement statement = null;
        ResultSet res = null;
        List<Emp> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder("select * from Emp where 1=1 ");
        if (ename != null && !ename.equals("")) {
            sql.append(" and name like ?");
            params.add("%" + ename + "%");
        }
        if (eage != -1) {
            sql.append(" and age = ?");
            params.add(eage);
        }

        sql.append(" limit ?,?");

        try {
            statement = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }
            statement.setObject(params.size() + 1, (current - 1) * limit);
            statement.setObject(params.size() + 2, limit);
            res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                int age = res.getInt("age");
                String birthday = res.getString("birthday");
                int sex = res.getInt("sex");
                int deptid = res.getInt("deptid");
                String filePath = res.getString("filePath");
                String fileHash = res.getString("fileHash");
                Emp emp = new Emp();
                emp.setId(id);
                emp.setName(name);
                emp.setAge(age);
                emp.setDate(birthday);
                emp.setSex(sex);
                emp.setDeptID(deptid);
                emp.setFilePath(filePath);
                emp.setFileHash(fileHash);
                list.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(res, statement, conn);
        }
        return list;
    }

    public int getCount() {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement statement = null;
        ResultSet res = null;
        String sql = "SELECT count(*) AS count FROM Emp";
        int count = 0;
        try {
            statement = conn.prepareStatement(sql);
            res = statement.executeQuery();
            while (res.next()) {
                count = res.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(res, statement, conn);
        }
        return count;
    }

    public int getCountLike(String ename, int eage) {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement statement = null;
        ResultSet res = null;

        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select count(id) as count from Emp where 1=1 ");
        if (ename != null && !ename.equals("")) {
            sql.append(" and name like ?");
            params.add("%" + ename + "%");
        }
        if (eage != -1) {
            sql.append(" and age = ?");
            params.add(eage);
        }

        int count = 0;
        try {
            statement = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }
            res = statement.executeQuery();
            while (res.next()) {
                count = res.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(res, statement, conn);
        }
        return count;
    }

}
