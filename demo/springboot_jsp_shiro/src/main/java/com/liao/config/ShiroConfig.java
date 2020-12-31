package com.liao.config;

import com.liao.realm.CustomRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * shiro配置
 */
@Configuration
public class ShiroConfig {

    //1.创建ShiroFilter
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultSecurityManager defaultSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //注入DefaultSecurityManager
        shiroFilterFactoryBean.setSecurityManager(defaultSecurityManager);
        Map<String,String> chainDefinitionMap = new HashMap<>();
        //配置放行资源
        chainDefinitionMap.put("/login", "anon");
        chainDefinitionMap.put("/login.jsp", "anon");
        chainDefinitionMap.put("/register.jsp", "anon");
        chainDefinitionMap.put("/register", "anon");
        //配置受限资源
        chainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chainDefinitionMap);
        //配置登录页
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
        return shiroFilterFactoryBean;
    }

    //2.创建安全管理器,DefaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm getRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //注入自定义realm
        defaultWebSecurityManager.setRealm(getRealm);
        return defaultWebSecurityManager;
    }
    //3.创建自定义的realm
    @Bean
    public Realm getRealm(){
        return new CustomRealm();
    }
}
