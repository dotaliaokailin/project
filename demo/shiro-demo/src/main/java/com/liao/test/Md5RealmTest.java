package com.liao.test;

import com.liao.custom.Md5RealmCustom;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

public class Md5RealmTest {
    public static void main(String[] args) {
        //创建安全管理器
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //注入MD5 realm
        defaultSecurityManager.setRealm(new Md5RealmCustom());
        //将安全管理其注入到安全工具中
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //通过安全工具获取主体 Subject
        Subject subject = SecurityUtils.getSubject();
        //认证
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("liao", "123456");
        try{
            //登录
            subject.login(usernamePasswordToken);
            System.out.println("登录成功：" + subject.isAuthenticated());
        }catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("用户名不存在");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }

        //权限认证
        if(subject.isAuthenticated()){
            System.out.println("是否具有admin角色：" + subject.hasRole("admin"));
            System.out.println("是否具有相应的权限：");
            boolean[] hasRoles = subject.hasRoles(Arrays.asList("admin", "user"));
            for (boolean hasRole : hasRoles) {
                System.out.println(hasRole);
            }
            System.out.println("是否同时具有相应的权限："+ subject.hasAllRoles(Arrays.asList("admin", "user")));

            System.out.println("是否具有操作用户01的所有资源: "+ subject.isPermitted("user:*:01"));
            System.out.println("是否具有相应的资源：");
            boolean[] permitted = subject.isPermitted("user:*:01", "user:create:01");
            for (boolean b : permitted) {
                System.out.println(b);
            }
            System.out.println("是否同时具有相应的资源：" + subject.isPermittedAll("user:*:01", "user:create:01"));
        }
    }
}
