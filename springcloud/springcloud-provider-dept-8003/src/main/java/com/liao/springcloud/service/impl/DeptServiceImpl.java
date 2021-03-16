package com.liao.springcloud.service.impl;

import com.liao.springcloud.dao.DeptDao;
import com.liao.springcloud.pojo.Dept;
import com.liao.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门实现类
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    /**
     * 增加部门
     */
    @Override
    public boolean addDept(Dept dept) {
        return deptDao.addDept(dept);
    }

    /**
     * 通过ID查询部门
     */
    @Override
    public Dept queryById(Long id) {
        return deptDao.queryById(id);
    }

    /**
     * 查询所有部门
     */
    @Override
    public List<Dept> queryAll() {
        return deptDao.queryAll();
    }
}
