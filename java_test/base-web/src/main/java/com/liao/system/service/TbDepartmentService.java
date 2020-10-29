package com.liao.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liao.system.pojo.TbDepartment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
public interface TbDepartmentService extends IService<TbDepartment> {
    /**
     * 查询部门和人数
     */
    public List<TbDepartment> findDeptAndCount();

    /**
     * 部门分页查询
     */
    public IPage<TbDepartment> getDeptPage(Integer currentPage, Integer pageSize, String name);

    /**
     * 新增修改部门信息
     * @param tbDepartment
     * @return
     */
    boolean savoOrUpdateDept(TbDepartment tbDepartment);

    /**
     * 导出部门信息Excel
     * @return
     */
    List<TbDepartment> exportExcel();
}
