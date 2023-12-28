package org.silentiger.dto.oauth2;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Oauth2TokenDto类
 * 自定义token信息返回内容
 *
 * @Author silentiger@yyh
 * @Date 2023-12-25 17:09:57
 */

@Data
@EqualsAndHashCode
@Builder
public class Oauth2TokenDto {
    // jti : JWT ID jwt唯一id，防止被重复使用
    private String jti;
    // 访问令牌
    private String token;
    // 刷新令牌
    private String refreshToken;
    // 访问令牌头前缀
    private String tokenHead;
    // 有效时间（秒）
    private Integer expiresIn;
    // 用户名
    private String username;
}
