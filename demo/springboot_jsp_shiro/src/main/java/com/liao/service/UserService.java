package com.liao.service;

import com.liao.pojo.User;

/**
 * 用户service
 */
public interface UserService {
    /**
     * 用户注册
     */
    public void save(User user);

    /**
     * 根据用户名查询实体
     */
    public User findByUsername(String username);
}
