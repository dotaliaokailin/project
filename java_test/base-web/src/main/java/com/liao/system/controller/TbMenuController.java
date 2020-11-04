package com.liao.system.controller;


import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbMenu;
import com.liao.system.service.TbMenuService;
import com.liao.system.service.TbRoleMenuService;
import com.liao.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @GetMapping("/menus")
    @ApiOperation(value = "所有菜单信息", notes = "获取所有菜单信息")
    public Result menus(){
        return Result.ok().data("menus",tbMenuService.list());
    }

    @GetMapping("/menuTree")
    @ApiOperation(value = "所有菜单树信息", notes = "获取所有菜单树信息")
    public Result menuTree(){
        List<TbMenu> tbMenus = tbMenuService.menuTree();
        if(!CollectionUtils.isEmpty(tbMenus)){
            return Result.ok().data("menuTree",tbMenus);
        }else{
            throw new BusinessException(ResultCodeEnum.MENU_NOT_FOUND_PAGE.getCode(), ResultCodeEnum.MENU_NOT_FOUND_PAGE.getMessage());
        }
    }

    @GetMapping("/menuButtonTree")
    @ApiOperation(value = "菜单按钮树接口", notes = "菜单按钮树")
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
    public void exportExcel(HttpServletResponse response){
        try {
            ExcelUtil.download(response, "菜单列表", "菜单列表" , tbMenuService.exportExcel(), TbMenu.class);
        } catch (Exception e){
            throw new BusinessException(ResultCodeEnum.EXPORT_FAIL.getCode(), ResultCodeEnum.EXPORT_FAIL.getMessage());
        }
    }
}

