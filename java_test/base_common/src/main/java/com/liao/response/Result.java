package com.liao.response;

import com.google.common.base.Supplier;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 链式编程
 * 返回结果集
 */
@Data
@ApiModel(value = "返回结果集")
public class Result {
    @ApiModelProperty(value = "状态")
    private Boolean status;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回Data")
    private Map<String, Object> data = new HashMap<>();

    //创建私有保护
    private Result(){

    }


    //创建对象
    private static Result getInstance(Boolean status, Integer code, String message){
        Supplier<Result> supplier = Result::new;
        Result result = supplier.get();
        result.setStatus(status);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    //成功
    public static Result ok(){
        return getInstance(true, ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage());
    }

    //失败
    public static Result error(){
        return getInstance(false, ResultCodeEnum.COMMON_FAIL.getCode(), ResultCodeEnum.COMMON_FAIL.getMessage());
    }

    //自定义返回成功与否
    public Result status(Boolean status){
        this.status = status;
        return this;
    }

    public Result code(Integer code){
        this.code = code;
        return this;
    }

    public Result message(String message){
        this.message = message;
        return this;
    }

    public Result data(String key, Object value){
        this.data.put(key ,value);
        return this;
    }

    public Result data(Map<String, Object> data){
       this.setData(data);
       return this;
    }
}
