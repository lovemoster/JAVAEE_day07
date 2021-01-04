package com.powernode.service.impl;

import com.powernode.dao.EmpDao;
import com.powernode.pojo.Emp;
import com.powernode.service.EmpService;

import java.util.List;

public class EmpServiceImpl implements EmpService {
    @Override
    public List<Emp> findAllEmp() {
        EmpDao empDao = new EmpDao();
        return empDao.queryAllEmp();
    }

    @Override
    public void addEmp(Emp emp) {
        EmpDao empDao = new EmpDao();
        empDao.insertEmp(emp);
    }

    @Override
    public void remove(int id) {
        EmpDao empDao = new EmpDao();
        empDao.deleteEmpByID(id);
    }

    @Override
    public void editEmp(Emp emp) {
        EmpDao empDao = new EmpDao();
        empDao.editEmp(emp);
    }

    @Override
    public List<Emp> getData(int current, int limit) {
        EmpDao empDao = new EmpDao();
        return empDao.getData(current, limit);
    }

    public List<Emp> getDataLike(int current, int limit, String name, int age) {
        EmpDao empDao = new EmpDao();
        return empDao.getDataLike(current, limit, name, age);
    }

    @Override
    public int getCount() {
        EmpDao empDao = new EmpDao();
        return empDao.getCount();
    }

    public int getCountLike(String name, int age) {
        EmpDao empDao = new EmpDao();
        return empDao.getCountLike(name, age);
    }
}
