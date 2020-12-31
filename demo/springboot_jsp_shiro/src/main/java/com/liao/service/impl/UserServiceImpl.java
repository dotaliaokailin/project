package com.liao.service.impl;

import com.liao.dao.UserDao;
import com.liao.pojo.User;
import com.liao.service.UserService;
import com.liao.util.SaltUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户ServiceImpl
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 用户注册
     */
    @Override
    public void save(User user) {
        String salt = SaltUtil.getSalt(8);
        user.setPassword(new Md5Hash(user.getPassword(), salt, 1024).toHex());
        user.setSalt(salt);
        userDao.save(user);
    }

    /**
     * 根据用户名查询实体
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
