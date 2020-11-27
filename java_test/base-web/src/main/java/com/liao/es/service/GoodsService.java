package com.liao.es.service;

import java.util.List;
import java.util.Map;

/**
 * 商品业务层
 */
public interface GoodsService {
    /**
     * 添加商品到elasticsearch
     */
    public Boolean parse(String keyword);

    /**
     * 分页查询商品
     */
    public Map<String, Object> search(String keyword, Integer pageIndex, Integer pageSize);

    /**
     * 删除商品
     */
    boolean del(String id);
}
