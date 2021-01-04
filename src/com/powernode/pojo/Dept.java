package com.powernode.pojo;

public class Dept {
    private int deptID;
    private String deptName;

    public Dept() {
    }

    public int getDeptID() {
        return deptID;
    }

    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptID=" + deptID +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
