package com.liao.response;

public enum ResultCodeEnum implements CustomResultCode{
    /* 成功 */
    SUCCESS(200, "成功"),
    OP_SUCCESS(201, "操作成功"),
    OP_FAIL(202, "操作失败"),
    DELETE_SUCCESS(203, "删除成功"),
    DELETE_FAIL(204, "删除失败"),
    EXPORT_SUCCESS(205, "导出成功"),
    EXPORT_FAIL(206, "导出失败"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),

    /*部门错误*/
    DEPARTMENT_NOT_EXIST(3007, "部门不存在"),
    DEPARTMENT_ALREADY_EXIST(3008, "部门已存在"),

    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限"),

    /*异常错误*/
    NO_FOUND_USER_EXCEPTION(9000, "用户不存在"),
    NO_FOUND_USER_PAGE_EXCEPTION(9001, "用户数据为空"),
    NO_FOUND_DEPARTMENTS(9100, "部门不存在");

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
