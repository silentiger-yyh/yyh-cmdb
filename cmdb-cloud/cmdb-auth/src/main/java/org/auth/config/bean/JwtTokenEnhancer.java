package org.auth.config.bean;

import org.silentiger.entity.SysUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置JwtTokenEnhancer添加自定义信息 ,继承TokenEnhancer实现一个JWT内容增强器
 *
 * @Author silentiger@yyh
 * @Date 2023-12-17 15:44:26
 */

@Component(value = "jwtTokenEnhancer")
public class JwtTokenEnhancer implements TokenEnhancer {

    /**
     * JWT内容增强器
     * @param accessToken the current access token with its expiration and refresh token
     * @param authentication the current authentication including client and user details
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        SysUser user = (SysUser) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>();
//        info.put("enhance", "增强的信息");
        // 将username字段设置到TOKEN中，Oauth2默认用户名字段是user_name
        info.put("username", user.getUsername());
        //给的参数是oAuth2的AccessToken，实现类是DefaultOAuth2AccessToken，
        //里面有个setAdditionalInformation方法添加自定义信息（Map类型）
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
