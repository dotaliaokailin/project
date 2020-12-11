package com.liao.system.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 公告
 * </p>
 *
 * @author liao
 * @since 2020-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbNotices对象", description="")
public class TbNotices implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "公告ID")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date modifiedTime;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "用户主键ID")
    private Long userId;

    @ApiModelProperty(value = "是否放入公告栏",notes = "默认0不放入，1是放入" )
    private Integer isNotices;

    @ApiModelProperty(value = "图片")
    private String image;
}
