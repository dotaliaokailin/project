package com.liao.system.enu.user;

/**
 * 用户状态枚举
 */
public enum UserStatusEnum {
    VALID_STATUS(1),//活跃
    LOCK_STATUS(0)//锁定
    ;

    private Integer status;

    UserStatusEnum(Integer status){
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
