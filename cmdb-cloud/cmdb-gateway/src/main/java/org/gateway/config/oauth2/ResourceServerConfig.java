package org.gateway.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig extends ResourceServerConfigurerAdapter { //继承此类进行高度自定义

    @Autowired
    private JwtAccessManager jwtAccessManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers(IgnoreUrlsConfig.urls).permitAll()
                .anyExchange().access(jwtAccessManager)
                .and()
                .csrf().disable();  // 不加这个会报错：An expected CSRF token cannot be found
        return http.build();
    }
}