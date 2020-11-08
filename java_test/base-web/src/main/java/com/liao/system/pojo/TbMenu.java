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
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 * <p>
 * 菜单表
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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbMenu对象", description="菜单表")
public class TbMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单/按钮ID")
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelProperty(value = "菜单/按钮ID", index = 0)
    private Long id;

    @ApiModelProperty(value = "上级菜单ID")
    @ExcelIgnore
    private Long parentId;

    @ApiModelProperty(value = "菜单/按钮名称")
    @ExcelProperty(value = "菜单/按钮名称", index = 1)
    private String menuName;

    @ApiModelProperty(value = "菜单URL")
    @ExcelProperty(value = "菜单URL", index = 2)
    private String url;

    @ApiModelProperty(value = "权限标识")
    @ExcelProperty(value = "权限标识", index = 3)
    private String perms;

    @ApiModelProperty(value = "图标")
    @ExcelProperty(value = "图标", index = 4)
    private String icon;

    @ApiModelProperty(value = "类型 0菜单 1按钮")
    @ExcelProperty(value = "类型 0菜单 1按钮", index = 5)
    private String type;

    @ApiModelProperty(value = "排序")
    @ExcelProperty(value = "排序", index = 6)
    private Long orderNum;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value = "创建时间", index = 7)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改时间")
    @ExcelProperty(value = "修改时间", index = 8)
    private Date modifiedTime;

    @ApiModelProperty(value = "0：不可用，1：可用")
    @ExcelProperty(value = "0：不可用，1：可用", index = 9)
    private Integer available;

    @ApiModelProperty(value = "0:不展开，1：展开")
    @ExcelProperty(value = "0:不展开，1：展开", index = 10)
    private Integer open;

    @TableField(exist = false)
    @ApiModelProperty(value = "子菜单集合")
    @ExcelIgnore
    private List<TbMenu> children;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否禁用")
    @ExcelIgnore
    private Boolean disabled;

    @TableField(exist = false)
    @ApiModelProperty(value = "父级菜单名称")
    @ExcelProperty(value = "父级菜单名称", index = 11)
    private String parentName;

    @TableField(exist = false)
    @ApiModelProperty(value = "新增或者修改，新增 true, 修改false")
    @ExcelIgnore
    private Boolean flag;
}
