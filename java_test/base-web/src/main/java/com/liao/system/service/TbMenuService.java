package com.liao.system.service;

import com.liao.system.pojo.TbMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
public interface TbMenuService extends IService<TbMenu> {

    /**
     *  通过父级菜单ID及菜单类型查询子菜单集合
     * @param parentId  父级菜单ID
     * @return
     */
    public List<TbMenu> findMenusByParentId(Long parentId);

    /**
     * 返回菜单树
     */
    public List<TbMenu> menuTree();

    /**
     * 返回菜单和按钮树
     */
    public List<TbMenu> menuButtonTree();

    /**
     * 通过角色ID返回菜单权限
     */
    public List<TbMenu> getMenuByRoleId(Long roleId);

    /**
     * 根据ID查询菜单信息
     * @param id
     * @return
     */
    TbMenu getMenuById(Long id);

    /**
     * 新增修改菜单信息
     * @param tbMenu
     */
    Boolean saveOrUpdateMenu(TbMenu tbMenu);

    /**
     * 导出菜单信息Excel
     * @return
     */
    List<TbMenu> exportExcel();

    /**
     * 根据用户菜单过滤菜单树
     * @param tbMenus
     * @param menus
     * @return
     */
    List<TbMenu> filterMenuTree(List<TbMenu> tbMenus, Set<Long> menus);
}
