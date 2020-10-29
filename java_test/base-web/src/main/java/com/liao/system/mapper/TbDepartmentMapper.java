package com.liao.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.system.pojo.TbDepartment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据部门名称查询部门信息
     * @param name
     * @return
     */
    @Select("select * from tb_department where name = #{name} limit 1")
    TbDepartment findDeptByName(String name);

    /**
     * 导出部门信息Excel
     * @return
     */
    List<TbDepartment> exportExcel();
}
