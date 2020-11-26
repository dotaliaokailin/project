package com.liao.es.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    /**
     * 商品名称
     */
    private String title;
    /**
     * 商品图片
     */
    private String img;
    /**
     * 商品价格
     */
    private String price;
}
