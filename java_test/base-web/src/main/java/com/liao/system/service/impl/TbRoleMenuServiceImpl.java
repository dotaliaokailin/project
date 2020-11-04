package com.liao.system.service.impl;

import com.liao.system.pojo.TbRoleMenu;
import com.liao.system.mapper.TbRoleMenuMapper;
import com.liao.system.service.TbRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@Service
public class TbRoleMenuServiceImpl extends ServiceImpl<TbRoleMenuMapper, TbRoleMenu> implements TbRoleMenuService {

    @Override
    public void deleteByRoleId(Long roleId) {
        this.baseMapper.deleteByRoleId(roleId);
    }

    /**
     * 根据角色ID查询关联的菜单
     *
     * @param roleId
     */
    @Override
    public List<TbRoleMenu> getMenuByRoleId(Long roleId) {
        return this.baseMapper.getMenuByRoleId(roleId);
    }

    /**
     * 根据菜单ID删除关联的角色
     *
     * @param id
     */
    @Override
    public void deleteByMenuId(Long menuId) {
        this.baseMapper.deleteByMenuId(menuId);
    }
}
