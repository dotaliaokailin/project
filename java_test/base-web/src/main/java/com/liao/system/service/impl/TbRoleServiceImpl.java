package com.liao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.system.pojo.TbRole;
import com.liao.system.mapper.TbRoleMapper;
import com.liao.system.service.TbRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param pageSize    页码容量
     * @param roleName    角色名
     * @return
     */
    @Override
    public Page<TbRole> findRolePage(Integer currentPage, Integer pageSize, String roleName) {
        QueryWrapper<TbRole> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(roleName)){
            queryWrapper.like("role_name", roleName);
        }
        return this.baseMapper.selectPage(new Page<TbRole>(currentPage, pageSize), queryWrapper);
    }
}
