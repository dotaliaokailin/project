package com.hrm.common.entity;

/**
 * 公共返回码
 */
public enum ResultCode {
    SUCCESS(true, 10000, "操作成功"),
    FAIL(false, 10001, "操作失败"),
    UNAUTHENTICATED(false, 10002, "未登录"),
    UNAUTHORISE(false, 10003, "权限不足"),
    ERROR(false, 99999, "系统繁忙，请稍后再试")

    //用户操作返回码

    //企业操作返回码

    //权限操作返回码

    //其他操作返回码
    ;

    //操作是否成功
    Boolean status;
    //操作代码
    Integer code;
    //提示信息
    String message;
    ResultCode(Boolean status, Integer code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
