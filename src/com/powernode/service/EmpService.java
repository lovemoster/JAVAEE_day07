package com.powernode.service;

import com.powernode.pojo.Emp;

import java.util.List;

public interface EmpService {
    List<Emp> findAllEmp();

    void addEmp(Emp emp);

    void remove(int id);

    void editEmp(Emp emp);

    List<Emp> getDataLike(int current, int limit, String name, int age);

    List<Emp> getData(int current, int limit);

    int getCount();

    int getCountLike(String name, int age);
}
