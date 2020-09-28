package com.liao.handler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "业务异常处理类")
public class BusinessException extends RuntimeException {
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回信息")
    private String message;
}
