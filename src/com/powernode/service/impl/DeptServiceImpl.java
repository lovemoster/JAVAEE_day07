package com.powernode.service.impl;

import com.powernode.dao.DeptDao;
import com.powernode.pojo.Dept;
import com.powernode.service.DeptService;

import java.util.List;

public class DeptServiceImpl implements DeptService {
    @Override
    public List<Dept> findAllDept() {
        DeptDao deptDao = new DeptDao();
        return deptDao.queryAllDept();
    }
}
