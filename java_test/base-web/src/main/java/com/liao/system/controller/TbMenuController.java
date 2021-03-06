package com.liao.system.controller;


import cn.hutool.core.util.ArrayUtil;
import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbMenu;
import com.liao.system.pojo.TbRole;
import com.liao.system.pojo.TbUser;
import com.liao.system.pojo.TbUserRole;
import com.liao.system.service.*;
import com.liao.util.ExcelUtil;
import com.liao.util.JWTTokenUtil;
import com.liao.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/system/tb-menu")
@Api(value = "菜单模块", tags = "菜单接口")
public class TbMenuController {
    @Autowired
    private TbMenuService tbMenuService;
    @Autowired
    private TbRoleMenuService tbRoleMenuService;
    @Autowired
    private TbUserRoleService tbUserRoleService;
    @Autowired
    private TbRoleService tbRoleService;
    @Autowired
    private TbUserService tbUserService;


    @GetMapping("/menus")
    @ApiOperation(value = "所有菜单信息", notes = "获取所有菜单信息")
    @PreAuthorize("isAuthenticated()")
    public Result menus(){
        return Result.ok().data("menus",tbMenuService.list());
    }

    @GetMapping("/menuTree")
    @ApiOperation(value = "所有菜单树信息", notes = "获取所有菜单树信息")
    @PreAuthorize("isAuthenticated()")
    public Result menuTree(HttpServletRequest request){
        List<TbMenu> tbMenus = tbMenuService.menuTree();
        if(!CollectionUtils.isEmpty(tbMenus)){
            TbUser tbUser = tbUserService.selectByUsername(JWTTokenUtil.getUsername(request.getHeader(JWTTokenUtil.TOKEN_HEADER).replace(JWTTokenUtil.TOKEN_PREFIX,"")));
            List<TbUserRole> roles = tbUserRoleService.listByUserId(tbUser.getId());
            Set<Long> menuSet = new HashSet<>();
            Set<String> buttonSet = new HashSet<>();
            if(!CollectionUtils.isEmpty(roles)){
                roles.forEach(role -> {
                    //菜单
                    List<TbMenu> menus = tbMenuService.getMenuByRoleId(tbRoleService.selectById(role.getRoleId()).getId());
                    if(!CollectionUtils.isEmpty(menus)){
                        menus.forEach(menu -> {
                            if("0".equals(menu.getType())){
                                menuSet.add(menu.getId());//菜单
                            }else{
                                buttonSet.add(menu.getPerms());//按钮
                            }
                        });
                    }
                });
            }
            return Result.ok().data("menuTree",tbMenuService.filterMenuTree(tbMenus, menuSet)).data("buttonTree", (buttonSet));
        }else{
            throw new BusinessException(ResultCodeEnum.MENU_NOT_FOUND_PAGE.getCode(), ResultCodeEnum.MENU_NOT_FOUND_PAGE.getMessage());
        }
    }

    @GetMapping("/menuButtonTree")
    @ApiOperation(value = "菜单按钮树接口", notes = "菜单按钮树")
    @PreAuthorize("isAuthenticated()")
    public Result menuButtonTree(){
        //拿到所有的菜单
        List<TbMenu> tbMenus = tbMenuService.menuButtonTree();
        if(!CollectionUtils.isEmpty(tbMenus)){
            return Result.ok().data("menuButtonTree",tbMenus);
        }else{
            throw new BusinessException(ResultCodeEnum.MENU_NOT_FOUND_PAGE.getCode(), ResultCodeEnum.MENU_NOT_FOUND_PAGE.getMessage());
        }
    }

    @GetMapping("/getMenuById")
    @ApiOperation(value = "根据ID查询菜单信息接口", notes = "根据ID查询菜单信息")
    @PreAuthorize("isAuthenticated()")
    public Result getMenuById(@RequestParam("id") Long id){
        TbMenu tbMenu = tbMenuService.getMenuById(id);
        if(null != tbMenu){
            return Result.ok().data("menu", tbMenu);
        }else{
            throw new BusinessException(ResultCodeEnum.MENU_NOT_FOUND.getCode(), ResultCodeEnum.MENU_NOT_FOUND.getMessage());
        }
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增修改菜单信息接口", notes = "新增修改菜单信息")
    @PreAuthorize("hasAnyAuthority('menu:add', 'menu:update')")
    public Result saveOrUpdate(@RequestBody TbMenu tbMenu){
        Boolean flag = tbMenuService.saveOrUpdateMenu(tbMenu);
        if(flag){
            return Result.ok().code(ResultCodeEnum.OP_SUCCESS.getCode()).message(ResultCodeEnum.OP_SUCCESS.getMessage());
        }else{
            throw new BusinessException(ResultCodeEnum.OP_FAIL.getCode(), ResultCodeEnum.OP_FAIL.getMessage());
        }
    }

    @GetMapping("/deleteById")
    @ApiOperation(value = "根据DI删除菜单接口", notes = "根据DI删除菜单")
    @Transactional(propagation = Propagation.NESTED)
    @PreAuthorize("hasAuthority('menu:delete')")
    public Result deleteById(@RequestParam("id") Long id){
        List<TbMenu> childMenus = tbMenuService.findMenusByParentId(id);
        if(!CollectionUtils.isEmpty(childMenus)){
            throw new BusinessException(ResultCodeEnum.MENU_CHILDREN_HAS_EXIST.getCode(), ResultCodeEnum.MENU_CHILDREN_HAS_EXIST.getMessage());
        }
        try{
            tbMenuService.removeById(id);
            tbRoleMenuService.deleteByMenuId(id);
            return Result.ok().code(ResultCodeEnum.DELETE_SUCCESS.getCode()).message(ResultCodeEnum.DELETE_SUCCESS.getMessage());
        }catch (Exception e){
            //设置手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BusinessException(ResultCodeEnum.DELETE_FAIL.getCode(), ResultCodeEnum.DELETE_FAIL.getMessage());
        }
    }

    @GetMapping("/exportExcel")
    @ApiOperation(value = "导出菜单信息Excel接口", notes = "导出菜单信息Excel")
    @PreAuthorize("hasAuthority('menu:export')")
    public void exportExcel(HttpServletResponse response){
        try {
            ExcelUtil.download(response, "菜单列表", "菜单列表" , tbMenuService.exportExcel(), TbMenu.class);
        } catch (Exception e){
            throw new BusinessException(ResultCodeEnum.EXPORT_FAIL.getCode(), ResultCodeEnum.EXPORT_FAIL.getMessage());
        }
    }
}

