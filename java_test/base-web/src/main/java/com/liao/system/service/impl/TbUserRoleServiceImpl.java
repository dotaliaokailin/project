package com.liao.system.service.impl;

import com.liao.system.pojo.TbUserRole;
import com.liao.system.mapper.TbUserRoleMapper;
import com.liao.system.service.TbUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@Service
public class TbUserRoleServiceImpl extends ServiceImpl<TbUserRoleMapper, TbUserRole> implements TbUserRoleService {
    @Override
    public List<TbUserRole> listByUserId(Long userId) {
        return this.baseMapper.listByUserId(userId);
    }

    /**
     * 根据用户ID删除用户角色数据
     *
     * @param userId
     */
    @Override
    public void removeByUserId(Long userId) {
        this.baseMapper.removeByUserId(userId);
    }
}
