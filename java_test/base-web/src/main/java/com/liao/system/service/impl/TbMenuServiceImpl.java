package com.liao.system.service.impl;

import com.liao.system.pojo.TbMenu;
import com.liao.system.mapper.TbMenuMapper;
import com.liao.system.service.TbMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@Service
public class TbMenuServiceImpl extends ServiceImpl<TbMenuMapper, TbMenu> implements TbMenuService {


    /**
     * 通过父级菜单ID查询子菜单集合
     *
     * @param parentId
     */
    @Override
    public List<TbMenu> findMenusByParentId(Long parentId, Integer type) {
        return this.baseMapper.findMenuByParentId(parentId, type);
    }

    /**
     * 返回菜单树
     */
    @Override
    public List<TbMenu> menuTree() {
        return appendChildMenu(findMenusByParentId(0L, 0));
    }

    public List<TbMenu> appendChildMenu(List<TbMenu> parentMenus){
        parentMenus.forEach(parentMenu -> {
            List<TbMenu> childMenus = findMenusByParentId(parentMenu.getId(), 0);
            parentMenu.setChildren(childMenus);
            appendChildMenu(childMenus);
        });
        return parentMenus;
    }
}
