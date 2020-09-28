package com.liao.system.pojo;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="BizProduct对象", description="")
public class BizProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "商品编号")
    private String pNum;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "图片")
    private String imageUrl;

    @ApiModelProperty(value = "规格型号")
    private String model;

    @ApiModelProperty(value = "计算单位")
    private String unit;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;

    @ApiModelProperty(value = "1级分类")
    private Long oneCategoryId;

    @ApiModelProperty(value = "2级分类")
    private Long twoCategoryId;

    @ApiModelProperty(value = "3级分类")
    private Long threeCategoryId;

    @ApiModelProperty(value = "是否删除:1物资正常,0:物资回收,2:物资审核中")
    private Integer status;


}
