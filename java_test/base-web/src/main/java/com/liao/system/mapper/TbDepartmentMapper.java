package com.liao.system.mapper;

import com.liao.system.pojo.TbDepartment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
    public List<TbDepartment> findDeptAndCount();
}
