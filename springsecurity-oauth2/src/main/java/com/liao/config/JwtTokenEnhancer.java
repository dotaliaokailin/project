package com.liao.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义enhance
 * 要继承TokenEnhancerChain
 */
public class JwtTokenEnhancer extends TokenEnhancerChain {

    /**
     * 实现自定义enhance
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> enhanceMap = new HashMap<>();
        enhanceMap.put("enhance", "我的自定义的信息");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(enhanceMap);
        return accessToken;
    }
}
