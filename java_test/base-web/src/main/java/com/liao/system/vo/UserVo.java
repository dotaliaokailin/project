package com.liao.system.vo;

import lombok.Data;

@Data
public class UserVo {
    private String username;

    private Integer sex;

    private String email;

    private Long departmentId;

    private String nickname;
}
