package com.liao.service;

import java.util.List;
import java.util.Map;

/**
 * 商品service
 */
public interface GoodService {
    /**
     * 添加商品到elasticsearch
     */
    public Boolean parse(String keyword);

    /**
     * 分页查询商品
     */
    public Map<String, Object> search(String keyword, Integer pageIndex, Integer pageSize);
}
