package com.liao.system.service.impl;

import com.liao.system.pojo.TbRole;
import com.liao.system.mapper.TbRoleMapper;
import com.liao.system.service.TbRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@Service
public class TbRoleServiceImpl extends ServiceImpl<TbRoleMapper, TbRole> implements TbRoleService {
    @Override
    public TbRole selectById(Long id) {
        return this.baseMapper.selectById(id);
    }
}
