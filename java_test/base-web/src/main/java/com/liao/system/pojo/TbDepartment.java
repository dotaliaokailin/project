package com.liao.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;

import java.beans.Transient;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="TbDepartment对象", description="")
public class TbDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "系名")
    private String name;

    @ApiModelProperty(value = "系办公电话")
    private String phone;

    @ApiModelProperty(value = "办公室地点")
    private String address;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;

    @ApiModelProperty(value = "系主任id，关联用户表")
    private Long mgrId;

    @ApiModelProperty(value = "部门的人数")
    @TableField(exist = false)//实体类中引入非数据库字段,用来接收分组查询的组的人数
    private Long deptCount;
}
