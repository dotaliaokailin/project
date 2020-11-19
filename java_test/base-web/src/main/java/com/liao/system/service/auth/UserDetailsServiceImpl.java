package com.liao.system.service.auth;

import com.liao.system.pojo.TbMenu;
import com.liao.system.pojo.TbRole;
import com.liao.system.pojo.TbUser;
import com.liao.system.pojo.TbUserRole;
import com.liao.system.service.TbMenuService;
import com.liao.system.service.TbRoleService;
import com.liao.system.service.TbUserRoleService;
import com.liao.system.service.TbUserService;
import com.liao.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private TbUserService tbUserService;
    @Autowired
    private TbRoleService tbRoleService;
    @Autowired
    private TbUserRoleService tbUserRoleService;
    @Autowired
    private TbMenuService tbMenuService;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(username)){
            throw new RuntimeException("用户名不能为空");
        }
        TbUser tbUser = tbUserService.selectByUsername(username);
        if(null == tbUser){
            throw new UsernameNotFoundException(String.format("%s用户不存在", username));
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<TbUserRole> roles = tbUserRoleService.listByUserId(tbUser.getId());
        if(!CollectionUtils.isEmpty(roles)){
            roles.forEach(role -> {
                //用户权限
                TbRole tbRole = tbRoleService.selectById(role.getRoleId());
                authorities.add(new SimpleGrantedAuthority("ROLE_"+tbRole.getRoleName()));
                //菜单
                List<TbMenu> menus = tbMenuService.getMenuByRoleId(tbRole.getId());
                if(!CollectionUtils.isEmpty(menus)){
                    menus.forEach(menu -> {
                        if(!StringUtils.isEmpty(menu.getPerms())){
                            authorities.add(new SimpleGrantedAuthority(menu.getPerms()));
                        }
                    });
                }
            });
        }
        tbUser.setAuthorities(authorities);
        return tbUser;
    }
}
