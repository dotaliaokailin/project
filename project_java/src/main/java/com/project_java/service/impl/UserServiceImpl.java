package com.project_java.service.impl;

import com.project_java.mapper.UserMapper;
import com.project_java.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserMapper {
    @Autowired
    UserMapper userMapper;

    @Override
    public Boolean save(User user) {
        try {
            userMapper.save(user);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public Boolean update(User user) {
        return userMapper.update(user);
    }

    @Override
    public User login(String username, String password) {
        return userMapper.login(username,password);
    }
}
