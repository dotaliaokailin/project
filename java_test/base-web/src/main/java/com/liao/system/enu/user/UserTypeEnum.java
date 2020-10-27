package com.liao.system.enu.user;

/**
 * 用户类型枚举
 */
public enum UserTypeEnum {
    ADMIN_TYPE(0),//管理员
    USER_TYPE(1)//系统用户
    ;

    private Integer type;

    UserTypeEnum(Integer type){
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
