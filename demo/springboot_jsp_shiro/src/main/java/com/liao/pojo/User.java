package com.liao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**主键ID*/
    private Integer id;
    /**用户名*/
    private String username;
    /**密码*/
    private String password;
    /**盐*/
    private String salt;
}
