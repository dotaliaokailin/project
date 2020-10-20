package com.liao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 实现UserDetailsService 的 loadUserByUsername方法
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        System.out.println("username = [" + username + "]");
        //默认是查数据库
        if(StringUtils.isEmpty(username) || !"admin".equals(username)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //查询到的密码加密
        String password = passwordEncoder.encode("123456");
        System.out.println("password = [" + password + "]");
        //权限，角色大小写名，角色的话要在前面加ROLE_
        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user,ROLE_admin,/main.html,ROLE_user,insert,update"));
    }
}
