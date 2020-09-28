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
@ApiModel(value="TbImage对象", description="")
public class TbImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "图片路径")
    private String path;

    @ApiModelProperty(value = "图片大小")
    private Long size;

    @ApiModelProperty(value = "图片类型")
    private String mediaType;

    @ApiModelProperty(value = "图片后缀")
    private String suffix;

    @ApiModelProperty(value = "图片高度")
    private Integer height;

    @ApiModelProperty(value = "图片宽度")
    private Integer width;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
