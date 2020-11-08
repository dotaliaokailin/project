package com.liao.system.service;

import com.liao.system.pojo.TbMenu;
import com.liao.system.pojo.TbRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 服务类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
public interface TbRoleMenuService extends IService<TbRoleMenu> {
    /**
     * 根据角色ID删除关联的菜单
     * @param roleId
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据角色ID查询关联的菜单
     * @param roleId
     */
    List<TbRoleMenu> getMenuByRoleId(Long roleId);

    /**
     * 根据菜单ID删除关联的角色
     * @param id
     */
    void deleteByMenuId(Long menuId);
}
