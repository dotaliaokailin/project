package com.liao.system.pojo;

import java.math.BigDecimal;
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
 * 操作日志表
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbLog对象", description="操作日志表")
public class TbLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "操作用户")
    private String username;

    @ApiModelProperty(value = "操作内容")
    private String operation;

    @ApiModelProperty(value = "耗时")
    private BigDecimal time;

    @ApiModelProperty(value = "操作方法")
    private String method;

    @ApiModelProperty(value = "方法参数")
    private String params;

    @ApiModelProperty(value = "操作者IP")
    private String ip;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "操作地点")
    private String location;


}
