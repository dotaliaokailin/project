package com.project_java.service;

import com.project_java.pojo.User;

public interface UserService {
    Boolean save(User user);

    User findByUserName(String username);

    Boolean update(User user);

    User login(String username,String password);
}
