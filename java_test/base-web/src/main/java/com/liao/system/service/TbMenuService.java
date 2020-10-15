package com.liao.system.service;

import com.liao.system.pojo.TbMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     * @param type 类型 0菜单 1按钮
     * @return
     */
    public List<TbMenu> findMenusByParentId(Long parentId, Integer type);

    /**
     * 返回菜单树
     */
    public List<TbMenu> menuTree();

}
