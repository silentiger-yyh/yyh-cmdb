package org.gateway.config.oauth2;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;


/**
 * url白名单
 *
 * @Author silentiger@yyh
 * @Date 2023-12-24 13:19:41
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class IgnoreUrlsConfig {
    public static final String[] urls = new String[]{
            "/doc.html",
            "/swagger-resources/**",
            "/swagger/**",
            "/*/v2/api-docs",
            "/*/*.js",
            "/*/*.css",
            "/*/*.png",
            "/*/*.ico",
            "/*.ico",
            "/webjars/**",
            "/actuator/**",
            "/*/oauth/token"
    };
}
