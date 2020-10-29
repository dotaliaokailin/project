package com.liao.system.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import java.beans.Transient;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 * <p>
 * 
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
// 头字体设置成20
@HeadFontStyle(fontHeightInPoints = 20)
// 头背景设置成红色 IndexedColors.RED.getIndex()
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 10)
// 内容的背景设置成绿色 IndexedColors.LIGHT_GREEN.getIndex()
@ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 42)
// 内容字体设置成18
@ContentFontStyle(fontHeightInPoints = 18)
@ContentRowHeight(30)
@HeadRowHeight(20)
@ColumnWidth(25)
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbDepartment对象", description="")
public class TbDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ExcelIgnore
    private Long id;

    @ApiModelProperty(value = "系名")
    @ExcelProperty(value = "部门名称", index = 0)
    private String name;

    @ApiModelProperty(value = "系办公电话")
    @ExcelProperty(value = "部门电话", index = 1)
    private String phone;

    @ApiModelProperty(value = "办公室地点")
    @ExcelProperty(value = "部门地址", index = 2)
    private String address;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    @ExcelProperty(value = "创建时间", index = 3)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    @ExcelProperty(value = "修改时间", index = 4)
    private Date modifiedTime;

    @ApiModelProperty(value = "系主任id，关联用户表")
    @ExcelIgnore
    private Long mgrId;

    @ApiModelProperty(value = "部门的人数")
    @TableField(exist = false)//实体类中引入非数据库字段,用来接收分组查询的组的人数
    @ExcelProperty(value = "部门总人数", index = 5)
    private Long deptCount;

    @ApiModelProperty(value = "系主任名称")
    @TableField(exist = false)
    @ExcelProperty(value = "部门经理", index = 6)
    private String username;
}
