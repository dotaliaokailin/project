package com.liao.controller;

import com.liao.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 商品控制器
 */
@RestController
public class GoodController {
    @Autowired
    private GoodService goodService;

    @GetMapping("/parse/{keyword}")
    public Boolean parse(@PathVariable("keyword") String keyword){
        return goodService.parse(keyword);
    }

    @GetMapping("/search/{keyword}/{pageIndex}/{pageSize}")
    public Map<String, Object> search(@PathVariable("keyword") String keyword, @PathVariable("pageIndex") Integer pageIndex, @PathVariable("pageSize") Integer pageSize){
        return goodService.search(keyword, pageIndex, pageSize);
    }

}
