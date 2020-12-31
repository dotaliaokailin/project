package com.liao.realm;

import com.liao.pojo.User;
import com.liao.service.UserService;
import com.liao.service.impl.UserServiceImpl;
import com.liao.util.ApplicationContextUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.ObjectUtils;

/**
 * 自定义realm,实现AuthorizingRealm
 */
public class CustomRealm extends AuthorizingRealm {
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if("admin".equals(primaryPrincipal)){
            simpleAuthorizationInfo.addRole("admin");
            simpleAuthorizationInfo.addStringPermission("user:*:*");
        }else{
            simpleAuthorizationInfo.addRole("user");
            simpleAuthorizationInfo.addStringPermission("user:select:*");
        }
        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String) token.getPrincipal();
        //上下文工具类拿到userService  bean
        //UserService userService = (UserService) ApplicationContextUtil.getBean("userServiceImpl");
        UserService userService = (UserService) ApplicationContextUtil.getBean(UserService.class);
        User user = userService.findByUsername(principal);
        if(!ObjectUtils.isEmpty(user)){
            return new SimpleAuthenticationInfo(principal, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
        }
        return null;
    }

    //凭证匹配器
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        //使用md5凭证匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //设置md5匹配器
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //设置hash值1024
        hashedCredentialsMatcher.setHashIterations(1024);
        super.setCredentialsMatcher(hashedCredentialsMatcher);
    }
}
