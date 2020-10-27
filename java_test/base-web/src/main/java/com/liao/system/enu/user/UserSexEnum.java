package com.liao.system.enu.user;

/**
 * 用户性别枚举
 */
public enum UserSexEnum {
    MAN_SEX(0), //男
    WOMAN_SEX(1), //女
    SECRET_SEX(2) //保密
    ;
    private Integer sex;

    UserSexEnum(Integer sex){
        this.sex = sex;
    }

    public Integer getSex() {
        return sex;
    }
}
