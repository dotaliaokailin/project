package com.liao.system.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
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
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 * <p>
 * 角色表
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
@ApiModel(value="TbRole对象", description="角色表")
public class TbRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelIgnore
    private Long id;

    @ApiModelProperty(value = "角色名称")
    @ExcelProperty(value = "角色名称", index = 0)
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    @ExcelProperty(value = "角色描述", index = 1)
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @ExcelProperty(value = "创建时间", index = 2)
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.UPDATE)
    @ExcelProperty(value = "修改时间", index = 3)
    private Date modifiedTime;

    @ApiModelProperty(value = "是否可用,0:不可用，1：可用")
    @ExcelIgnore
    private Integer status;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否可用")
    @ExcelProperty(value = "是否可用", index = 4)
    private String statusName;
}
