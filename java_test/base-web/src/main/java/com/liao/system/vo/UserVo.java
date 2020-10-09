package com.liao.system.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserVo {

    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String avatar;

    private String phoneNumber;

    private Integer status;

    private Date createTime;

    private Date modifiedTime;

    private Integer sex;

    private String salt;

    private Integer type;

    private String password;

    private Date birth;

    private Long departmentId;

    private Integer deleted;

    private String deptName;
}
