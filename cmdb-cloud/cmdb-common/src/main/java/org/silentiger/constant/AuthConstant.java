package org.silentiger.constant;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 用于授权的常量
 *
 * @Author silentiger@yyh
 * @Date 2023-12-20 21:33:06
 */

public class AuthConstant {
    /**
     * JWT认证信息请求头字段名称（预留）
     */
    public static final String JWT_HEADER = "ACCESS-TOKEN";
    /**
     * header令牌字段
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * access_token存储在redis中的key前缀，key为access:jwt
     */
    public static final String REDIS_KEY_ACCESS_TOKEN = "access:";
    /**
     * JWT令牌前缀(TOKEN类型)
     */
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    /**
     * JWT签名密钥
     */
    public static final String JWT_KEY = "3.1415926535";

    /**
     * PORTAL客户ID
     */
    public static final String PORTAL_CLIENT_ID = "silentiger-cmdb-client";
    /**
     * PORTAL客户端授权密钥
     */
    public static final String PORTAL_CLIENT_SECRET = "silentiger";

    /**
     * get获取token时的Authorization
     */
    public static String getOauthTokenAuthorization() {
        byte[] encode = Base64.getEncoder().encode((PORTAL_CLIENT_ID + ":" + PORTAL_CLIENT_SECRET).getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encode);
    }

}
