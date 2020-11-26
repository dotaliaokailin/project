package com.liao.es.controller;

import com.liao.es.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 商品控制器
 */
@RestController
@RequestMapping("/es")
public class GoodController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/parse/{keyword}")
    public Boolean parse(@PathVariable("keyword") String keyword){
        return goodsService.parse(keyword);
    }

    @GetMapping("/search/{keyword}/{pageIndex}/{pageSize}")
    public Map<String, Object> search(@PathVariable("keyword") String keyword, @PathVariable("pageIndex") Integer pageIndex, @PathVariable("pageSize") Integer pageSize){
        return goodsService.search(keyword, pageIndex, pageSize);
    }

}
