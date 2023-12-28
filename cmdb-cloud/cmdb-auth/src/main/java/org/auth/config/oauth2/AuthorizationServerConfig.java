package org.auth.config.oauth2;

import jdk.nashorn.internal.parser.TokenStream;
import org.auth.config.exception.Oauth2ExceptionHandler;
import org.auth.service.impl.UserDetailsServiceImpl;
import org.auth.config.bean.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 认证服务端配置
 * @Author silentiger@yyh
 * @Date 2023-12-17 14:10:01
 */

//@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    /**
     * 注入权限验证控制器 来支持 password grant type
     */
    @Autowired
//    @Resource(name = "authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;
    @Autowired
    @Qualifier(value = "tokenStore")
    private TokenStore tokenStore;
    /**
     * 注入userDetailsService，开启refresh_token需要用到
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    @Qualifier(value = "passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier(value = "jdbcClientDetailsService")
    private ClientDetailsService clientDetailsService;

    @Autowired
    private DataSource dataSource;
    /**
     * 单点登录配置
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        //必须要身份认证，单点登录必须要配置
        security.tokenKeyAccess("isAuthenticated()");
    }

    /**
     * 配置客户端，下面三种配置方式均可，imMemory不适合用在生产环境，
     * 数据库方式对应项目配置的数据源，表名为oauth_client_details，目前无法修改表名
     * @param clients the client details configurer
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(clientDetailsService);
        clients.jdbc(dataSource);
//        clients.inMemory()
//                .withClient("silentiger-cmdb-portal-client")
//                .authorizedGrantTypes("password", "refresh_token", "authorization_code", "client_credentials", "implicit")
//                .scopes("all")
//                /**
//                 * 客户端密钥
//                 * clientid是客户端标识，secret是客户端身份凭据，客户端通过提供凭据来证明自己的身份，以便获取访问令牌，从而访问受保护的资源。
//                 * 服务端通过clientid知道可能是你，但是服务端不信任你，你就得拿出证据，服务端才会给你颁发token。
//                 * 并且这个密钥只会存在于服务端，或者数据库，前端是通过clientId+secret组合编码，放到请求头Authorization中的
//                 * 见下面的main方法
//                 */
//                .secret(passwordEncoder.encode("silentiger"))
////                .secret("{noop}secret")
//                .accessTokenValiditySeconds(3600*24)
//                .refreshTokenValiditySeconds(3600*24*7)
//                // 如果要跳转的话需要加上，否则会404，但是这种写在代码里的方式肯定不实用，后期再整改
////                .redirectUris("https://baidu.com", "https://baidu.com2")
//                .and()
//                .withClient("silentiger-cmdb-admin-client")
//                .authorizedGrantTypes("password", "refresh_token", "authorization_code", "client_credentials", "implicit")
//                .scopes("all")
//                .secret(passwordEncoder.encode("secret"))
//                .accessTokenValiditySeconds(60 * 30)
//                .refreshTokenValiditySeconds(60 * 30)
////                .redirectUris("https://baidu.com", "https://baidu.com2")
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain chain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);//还要把转换器放进去用来实现jwtTokenEnhancer的互相转换
        chain.setTokenEnhancers(delegates);
        endpoints
//              //配置token存储方式
                .tokenStore(tokenStore)
//                .approvalStoreDisabled()
//                .userApprovalHandler(userApprovalHandler)
//                .exceptionTranslator(new Oauth2ExceptionHandler())
                //开启密码授权类型
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter)
                //要使用refresh_token的话，需要额外配置userDetailsService
                .userDetailsService(userDetailsService)
                .tokenEnhancer(chain); //设置JWT增强内容
    }

    /**
     * =======不能删==========
     * client_id+client_secret作为客户端访问授权的凭据
     * 生成的Basic 开头的编码放到请求头的Authorization字段
     */
    private static final String CLIENT = "silentiger-cmdb-portal-client:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzMSJdLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE3MDM2NDMzNjQsImp0aSI6IkZGWExWRjNlcVlOaUVacEhENjhZbmVWcmNuZyIsImNsaWVudF9pZCI6InNpbGVudGlnZXItbWFsbC1wb3J0YWwtY2xpZW50IiwidXNlcm5hbWUiOiJhZG1pbiJ9.DVnrWrzpk6t6Fy5HB2NqMhlNox01OfGcumJEZki4Jdg";
//    private static final String CLIENT = "silentiger-cmdb-portal-client:silentiger";
    public static void main(String[] args) {
        byte[] encode = Base64.getEncoder().encode(CLIENT.getBytes(StandardCharsets.UTF_8));
        System.out.println("Basic " + new String(encode));
    }

}
