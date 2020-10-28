package com.liao.system.service.impl;

import com.liao.system.pojo.TbMenu;
import com.liao.system.mapper.TbMenuMapper;
import com.liao.system.service.TbMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.system.service.TbRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private TbRoleMenuService tbRoleMenuService;

    /**
     * 通过父级菜单ID查询子菜单集合
     *
     * @param parentId
     */
    @Override
    public List<TbMenu> findMenusByParentId(Long parentId) {
        return this.baseMapper.findMenuByParentId(parentId);
    }

    /**
     * 返回菜单树
     */
    @Override
    public List<TbMenu> menuTree() {
        return appendChildMenu(findMenusByParentId(0L), "0");
    }

    /**
     * 返回菜单和按钮树
     */
    @Override
    public List<TbMenu> menuButtonTree() {
        return appendChildMenu(findMenusByParentId(0L), null);
    }

    /**
     * 拼接菜单树
     * @param parentMenus
     * @param type 菜单类型 0菜单 1按钮
     * @return
     */
    public List<TbMenu> appendChildMenu(List<TbMenu> parentMenus, String type){
        parentMenus.forEach(parentMenu -> {
            List<TbMenu> childMenus = findMenusByParentId(parentMenu.getId());
            if(!StringUtils.isEmpty(type)){//如果类型不为空，则进行过滤
                childMenus = childMenus.stream().filter(f -> type.equals(f.getType())).collect(Collectors.toList());
            }
            if(parentMenu.getAvailable() == 1){//是否禁用
                parentMenu.setDisabled(false);
            }else{
                parentMenu.setDisabled(true);
            }
            parentMenu.setChildren(childMenus);
            appendChildMenu(childMenus, type);
        });
        return parentMenus;
    }
}
