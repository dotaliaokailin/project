package com.liao.springcloud.dao;

import com.liao.springcloud.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liao
 * 部门dao
 */
@Mapper
@Repository
public interface DeptDao {
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
