package com.liao.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.system.pojo.TbDepartment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
public interface TbDepartmentMapper extends BaseMapper<TbDepartment> {
    /**
     * 查询部门和人数
     */
    public List<TbDepartment> findDeptAndCount();

    /**
     * 部门分页查询
     */
    IPage<TbDepartment> getDeptPage(Page<TbDepartment> page, @Param(Constants.WRAPPER) QueryWrapper<TbDepartment> wrapper);
}
