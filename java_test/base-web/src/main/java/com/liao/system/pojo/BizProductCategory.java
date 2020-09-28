package com.liao.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="BizProductCategory对象", description="")
public class BizProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类别id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "类别名称")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    private Date createTime;

    private Date modifiedTime;

    @ApiModelProperty(value = "父级分类id")
    private Long pid;


}
