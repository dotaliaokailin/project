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
@ApiModel(value="BizSupplier对象", description="")
public class BizSupplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "供应商名称")
    private String name;

    @ApiModelProperty(value = "供应商地址")
    private String address;

    @ApiModelProperty(value = "供应商邮箱")
    private String email;

    @ApiModelProperty(value = "供应商电话")
    private String phone;

    private Date createTime;

    private Date modifiedTime;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "联系人")
    private String contact;


}
