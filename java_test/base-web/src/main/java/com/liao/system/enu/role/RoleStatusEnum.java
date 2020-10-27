package com.liao.system.enu.role;

/**
 * 角色状态枚举
 */
public enum RoleStatusEnum {
    ENABLE(1),
    DISENABLE(0)
    ;
    private Integer status;

    RoleStatusEnum(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }
}
