package com.hrm.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页响应数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    //总数
    private Integer total;
    //数据
    private List<T> rows;
}
