package com.hrm.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据响应对象
 */
@Data
@NoArgsConstructor
public class Result {
    //状态
    private Boolean status;
    //返回码
    private Integer code;
    //消息
    private String message;
    //数据
    private Object data;

    public Result(ResultCode resultCode){
        this.status = resultCode.status;
        this.code = resultCode.code;
        this.message = resultCode.message;
    }

    public Result(ResultCode resultCode, Object data){
        this.status = resultCode.status;
        this.code = resultCode.code;
        this.message = resultCode.message;
        this.data = data;
    }

    public Result(Boolean status, Integer code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static Result SUCCESS(){
        return new Result(ResultCode.SUCCESS);
    }

    public static Result FAIL(){
        return new Result(ResultCode.FAIL);
    }

    public static Result ERROR(){
        return new Result(ResultCode.ERROR);
    }
}
