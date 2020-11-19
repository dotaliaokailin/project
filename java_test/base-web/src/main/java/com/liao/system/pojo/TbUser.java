package com.liao.system.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Collection;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 用户表
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
@ApiModel(value="TbUser对象", description="用户表")
public class TbUser implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelIgnore
    private Long id;

    @ApiModelProperty(value = "用户名")
    @ExcelProperty(value = "用户名", index = 0)
    private String username;

    @ApiModelProperty(value = "昵称")
    @ExcelProperty(value = "昵称", index = 1)
    private String nickname;

    @ApiModelProperty(value = "邮箱")
    @ExcelProperty(value = "邮箱", index = 2)
    private String email;

    @ApiModelProperty(value = "头像")
    @ExcelIgnore
    private String avatar;

    @ApiModelProperty(value = "联系电话")
    @ExcelProperty(value = "联系电话", index = 3)
    private String phoneNumber;

    @ApiModelProperty(value = "状态 0锁定 1有效")
    @ExcelIgnore
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)//自动填充，mybatis-plus
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @ExcelIgnore
    @TableField(fill = FieldFill.UPDATE)
    private Date modifiedTime;

    @ApiModelProperty(value = "性别 0男 1女 2保密")
    @ExcelIgnore
    private Integer sex;

    @ApiModelProperty(value = "盐")
    @ExcelIgnore
    private String salt;

    @ApiModelProperty(value = "0:超级管理员，1：系统用户")
    @ExcelIgnore
    private Integer type;

    @ApiModelProperty(value = "密码")
    @ExcelIgnore
    private String password;

    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    @ExcelProperty(value = "生日", index = 4)
    private Date birth;

    @ApiModelProperty(value = "部门id")
    @ExcelIgnore
    private Long departmentId;

    @ApiModelProperty(value = "是否删除")
    @ExcelIgnore
    private Integer deleted;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    @ExcelProperty(value = "部门", index = 5)
    private String deptName;

    /**
     * 性别
     */
    @TableField(exist = false)
    @ExcelProperty(value = "性别", index = 6)
    private String sexName;

    /**
     * 启用禁用
     */
    @TableField(exist = false)
    @ExcelProperty(value = "启用禁用", index = 7)
    private String statusName;

    /**
     * 用户拥有的菜单Id集合
     */
    @TableField(exist = false)
    @ExcelIgnore
    private Set<Long> menus;

    /**
     * 用户拥有的按钮权限名称集合
     */
    @TableField(exist = false)
    @ExcelIgnore
    private Set<String> buttons;

    @TableField(exist = false)
    @ExcelIgnore
    private Collection<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return this.authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}
