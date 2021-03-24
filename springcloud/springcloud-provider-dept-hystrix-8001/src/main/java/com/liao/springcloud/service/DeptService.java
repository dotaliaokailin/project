package com.liao.springcloud.service;

import com.liao.springcloud.pojo.Dept;

import java.util.List;

/**
 * 部门接口层
 */
public interface DeptService {
    /**
     * 增加部门
     */
    public boolean addDept(Dept dept);

    /**
     * 通过ID查询部门
     */
    public Dept queryById(Long id);

    /**
     * 查询所有部门
     */
    public List<Dept> queryAll();
}
