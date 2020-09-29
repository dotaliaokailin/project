package com.liao.system.service;

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
    public List<TbDepartment> findDeptAndCount();
}
