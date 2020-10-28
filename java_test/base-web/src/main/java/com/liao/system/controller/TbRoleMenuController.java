package com.liao.system.controller;


import cn.hutool.core.util.ArrayUtil;
import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbMenu;
import com.liao.system.pojo.TbRoleMenu;
import com.liao.system.service.TbMenuService;
import com.liao.system.service.TbRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 角色菜单关联表 前端控制器
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/system/tb-role-menu")
@Api(value = "角色菜单关系模块", tags = "角色菜单关系接口")
public class TbRoleMenuController {
    @Autowired
    private TbRoleMenuService tbRoleMenuService;
    @Autowired
    private TbMenuService tbMenuService;

    @GetMapping("/getMenuByRoleId")
    @ApiOperation(value = "全部菜单集合和某角色的菜单ID集合", notes = "全部菜单集合和某角色的菜单ID集合接口")
    public Result getMenuByRoleId(@RequestParam("id") Long id){
        //拿到角色的菜单关系数据
        List<TbRoleMenu> tbRoleMenus = tbRoleMenuService.getMenuByRoleId(id);
        //拿到菜单ID集合
        List<Long> menuIds = Arrays.asList(ArrayUtil.wrap(tbRoleMenus.stream().mapToLong(t -> t.getMenuId()).toArray()));
        //拿到所有的菜单
        List<TbMenu> tbMenus = tbMenuService.menuButtonTree();
        if(!CollectionUtils.isEmpty(tbMenus)){
            return Result.ok().data("menus", tbMenus).data("menuIds", menuIds);
        }else{
            throw new BusinessException(ResultCodeEnum.ROLE_NO_FOUND_MENU.getCode(), ResultCodeEnum.ROLE_NO_FOUND_MENU.getMessage());
        }
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存角色菜单关系操作", notes = "保存角色菜单关系操作接口")
    @Transactional(propagation = Propagation.NESTED)
    public Result save(@RequestBody List<Long> menuIds, @RequestParam("roleId") Long roleId){
        try{
            tbRoleMenuService.deleteByRoleId(roleId);
            if(!CollectionUtils.isEmpty(menuIds)){
                List<TbRoleMenu> list = new ArrayList<>();
                menuIds.forEach(m -> {
                    TbRoleMenu tbRoleMenu = new TbRoleMenu();
                    tbRoleMenu.setMenuId(m);
                    tbRoleMenu.setRoleId(roleId);
                    list.add(tbRoleMenu);
                });
                tbRoleMenuService.saveBatch(list, list.size());
            }
            return Result.ok().message(ResultCodeEnum.OP_SUCCESS.getMessage()).code(ResultCodeEnum.OP_SUCCESS.getCode());
        }catch (Exception e){
            //设置手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BusinessException(ResultCodeEnum.OP_FAIL.getCode(), ResultCodeEnum.OP_FAIL.getMessage());
        }
    }
}

