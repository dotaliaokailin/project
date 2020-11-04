package com.liao.response;
/**
 * @Author: NieChangan
 * @Description: 返回码定义
 * 规定:
 * #200表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 * #后面对什么的操作自己在这里注明就行了
 */
public enum ResultCodeEnum implements CustomResultCode{
    /* 成功 */
    SUCCESS(200, "成功"),
    OP_SUCCESS(201, "操作成功"),
    OP_FAIL(202, "操作失败"),
    DELETE_SUCCESS(203, "删除成功"),
    DELETE_FAIL(204, "删除失败"),
    EXPORT_SUCCESS(205, "导出成功"),
    EXPORT_FAIL(206, "导出失败"),
    UPLOAD_SUCCESS(207, "上传成功"),
    UPLOAD_FAIL(208, "上传失败"),
    AUTHORIZATION_SUCCESS(209, "授权成功"),
    AUTHORIZATION_FAIL(210, "授权失败"),

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
    USER_ACCOUNT_NOT_FOUND_PAGE(2010, "用户数据不存在"),

    /*部门错误*/
    DEPARTMENT_NOT_EXIST(3007, "部门不存在"),
    DEPARTMENT_ALREADY_EXIST(3008, "部门已存在"),
    DEPARTMENT_NOT_EXIST_PAGE(3009, "部门数据不存在"),

    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限"),

    /* 角色错误*/
    ROLE_NOT_FOUND_PAGE(4001, "角色数据不存在"),
    ROLE_ALREADY_EXIST( 4002, "角色已存在"),
    ROLE_NO_FOUND_MENU(4003, "角色菜单未找到"),

    /* 菜单错误*/
    MENU_NOT_FOUND_PAGE(5001, "菜单树不存在"),
    MENU_NOT_FOUND(5002, "菜单数据不存在"),
    MENU_ALREADY_FOUND(5003, "菜单名称已存在"),
    MENU_CHILDREN_HAS_EXIST(5004, "请先删除子菜单");

    /*异常错误*/

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
