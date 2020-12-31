package com.liao.dao;

import com.liao.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Dao
 */
@Mapper
public interface UserDao {
    /**
     * 注册用户
     */
    public void save(User user);

    /**
     * 根据用户名查询实体
     */
    public User findByUsername(String username);
}
