package com.liao;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

public class Test {
    public static void main(String[] args) {
        //1.创建默认的安全管理类
        SecurityManager securityManager = new DefaultSecurityManager();
        //设置realm
        ((DefaultSecurityManager)securityManager).setRealm(new IniRealm("classpath:shiro.ini"));
        //3. 安全管理工具设置安全管理类
        SecurityUtils.setSecurityManager(securityManager);
        //验证登陆
        AuthenticationToken usernamePasswordToken = new UsernamePasswordToken("liao","123456");
        //拿到主体
        Subject subject = SecurityUtils.getSubject();
        try{
            System.out.println("是否已验证：" + subject.isAuthenticated());
            //登陆验证
            subject.login(usernamePasswordToken);
            System.out.println("是否已验证：" + subject.isAuthenticated());
        }catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("账号错误");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }
    }
}
