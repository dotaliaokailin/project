package com.liao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Good {
    //标题
    private String title;
    //图片
    private String img;
    //价格
    private String price;
}
