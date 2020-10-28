package com.liao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.system.pojo.TbDepartment;
import com.liao.system.mapper.TbDepartmentMapper;
import com.liao.system.service.TbDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@Service
public class TbDepartmentServiceImpl extends ServiceImpl<TbDepartmentMapper, TbDepartment> implements TbDepartmentService {

    /**
     * 查询部门和人数
     */
    @Override
    public List<TbDepartment> findDeptAndCount() {
        return this.baseMapper.findDeptAndCount();
    }

    /**
     * 部门分页查询
     */
    @Override
    public IPage<TbDepartment> getDeptPage(Integer currentPage, Integer pageSize, String name) {
        QueryWrapper<TbDepartment> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){//部门名称
            queryWrapper.like("name", name);
        }
        return this.baseMapper.getDeptPage(new Page<TbDepartment>(currentPage, pageSize), queryWrapper);
    }
}
