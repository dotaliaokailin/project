package com.liao.system.service.impl;

import com.liao.system.pojo.TbDepartment;
import com.liao.system.mapper.TbDepartmentMapper;
import com.liao.system.service.TbDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public List<TbDepartment> findDeptAndCount() {
        return this.baseMapper.findDeptAndCount();
    }
}
