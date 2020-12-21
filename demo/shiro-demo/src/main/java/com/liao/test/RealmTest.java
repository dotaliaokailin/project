package com.liao.test;

import com.liao.custom.RealmCustom;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

public class RealmTest {
    public static void main(String[] args) {
        //创建defaultSecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //设置自定义realm
        defaultSecurityManager.setRealm(new RealmCustom());
        //把defaultSecurityManager放入securityUtils工具类
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //拿到认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("liao", "123456");
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("账号不存在");
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }
    }
}
