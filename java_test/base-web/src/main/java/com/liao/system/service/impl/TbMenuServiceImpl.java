package com.liao.system.service.impl;

import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbMenu;
import com.liao.system.mapper.TbMenuMapper;
import com.liao.system.service.TbMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.system.service.TbRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
     * 通过角色ID返回菜单权限
     *
     * @param roleId
     */
    @Override
    public List<TbMenu> getMenuByRoleId(Long roleId) {
        return this.baseMapper.getMenuByRoleId(roleId);
    }

    /**
     * 根据ID查询菜单信息
     *
     * @param id
     * @return
     */
    @Override
    public TbMenu getMenuById(Long id) {
        return this.baseMapper.getMenuById(id);
    }

    /**
     * 新增修改菜单信息
     *
     * @param tbMenu
     */
    @Override
    public Boolean saveOrUpdateMenu(TbMenu tbMenu) {
        //参数校验
        if( null == tbMenu
            || tbMenu.getParentId() < 0
            || StringUtils.isEmpty(tbMenu.getMenuName())
            || (0 != tbMenu.getAvailable() && 1 != tbMenu.getAvailable())
            || (0 != tbMenu.getOpen() && 1 != tbMenu.getOpen())
            || (!"0".equals(tbMenu.getType()) && !"1".equals(tbMenu.getType()))
            || tbMenu.getOrderNum() <= 0
            || null == tbMenu.getFlag()){
            throw new BusinessException(ResultCodeEnum.PARAM_NOT_COMPLETE.getCode(),ResultCodeEnum.PARAM_NOT_COMPLETE.getMessage());
        }
        TbMenu otherMenu = this.baseMapper.getMenuByName(tbMenu.getMenuName());
        if(tbMenu.getFlag()){//新增
            if(null != otherMenu){
                throw new BusinessException(ResultCodeEnum.MENU_ALREADY_FOUND.getCode(), ResultCodeEnum.MENU_ALREADY_FOUND.getMessage());
            }
        }else{//修改
            if(null != otherMenu && otherMenu.getId().compareTo(tbMenu.getId()) != 0){
                throw new BusinessException(ResultCodeEnum.MENU_ALREADY_FOUND.getCode(), ResultCodeEnum.MENU_ALREADY_FOUND.getMessage());
            }
        }
        tbMenu.setModifiedTime(null);
        tbMenu.setCreateTime(null);
        return this.saveOrUpdate(tbMenu);
    }

    /**
     * 导出菜单信息Excel
     *
     * @return
     */
    @Override
    public List<TbMenu> exportExcel() {
        return this.baseMapper.exportExcel();
    }

    /**
     * 根据用户菜单过滤菜单树
     *
     * @param tbMenus
     * @param menus
     * @return
     */
    @Override
    public List<TbMenu> filterMenuTree(List<TbMenu> tbMenus, Set<Long> menus) {
        if(CollectionUtils.isEmpty(menus)){
            return null;
        }
        List<TbMenu> menuList = tbMenus.stream().filter(tbMenu -> menus.contains(tbMenu.getId())).collect(Collectors.toList());
        menuList.forEach(c -> {
            List<TbMenu> children = c.getChildren();
            if(!CollectionUtils.isEmpty(children)){
                c.setChildren(this.filterMenuTree(children, menus));
            }
        });
        return menuList;
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
