package com.liao.es.controller;

import com.liao.es.service.GoodsService;
import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 商品控制器
 */
@RestController
@RequestMapping("/es")
@Api(value = "商品模块", tags = "商品模块接口")
public class GoodController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 爬取商品信息
     * @param keyword
     * @return
     */
    @GetMapping("/parse/{keyword}")
    @ApiOperation(value = "爬取商品信息", notes = "爬取商品信息接口")
    @PreAuthorize("hasAnyAuthority('shop:add')")
    public Result parse(@PathVariable("keyword") String keyword){
        boolean flag = goodsService.parse(keyword);
        if(flag)
            return Result.ok();
        else
            return Result.error();
    }

    /**
     * 分页查询商品
     * @param keyword
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/search")
    @ApiOperation(value = "分页查询商品信息", notes = "分页查询商品信息接口")
    @PreAuthorize("isAuthenticated()")
    public Result search(@RequestParam("keyword") String keyword, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize){
        return Result.ok().data(goodsService.search(keyword, pageIndex, pageSize));
    }

    /**
     * 删除商品
     */
    @PostMapping("/del")
    @ApiOperation(value = "删除商品", notes = "删除商品接口")
    @PreAuthorize("hasAuthority('shop:delete')")
    public Result del(@RequestParam("id") String id){
        boolean flag = goodsService.del(id);
        if(flag){
            return Result.ok().code(ResultCodeEnum.DELETE_SUCCESS.getCode()).message(ResultCodeEnum.DELETE_SUCCESS.getMessage());
        }else{
            throw new BusinessException(ResultCodeEnum.DELETE_FAIL.getCode(), ResultCodeEnum.DELETE_FAIL.getMessage());
        }
    }
}
