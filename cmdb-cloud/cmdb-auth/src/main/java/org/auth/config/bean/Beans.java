package org.auth.config.bean;

import org.auth.config.strategy.redistokenstore.FastjsonRedisTokenStoreSerializationStrategy;
import org.silentiger.constant.AuthConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * 注入Bean
 *
 * @Author silentiger@yyh
 * @Date 2023-12-17 15:44:26
 */

@Configuration
public class Beans {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    /**
     * 生成TokenStore来保存token  此处为JwtTokenStore实现
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
//        return new JdbcTokenStore( dataSource );
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        // 为了方便查看redis存储的信息，可以使用自定义的序列化器(同时解决在反序列化时也会报错)
        redisTokenStore.setSerializationStrategy(new FastjsonRedisTokenStoreSerializationStrategy());
        return redisTokenStore;
//        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    /**
     *  生成JwtAccessTokenConverter转换器，并设置密钥
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 设置jwt密钥
        jwtAccessTokenConverter.setSigningKey(AuthConstant.JWT_KEY);
        return jwtAccessTokenConverter;
    }

    /**
     * JwtTokenEnhancer的注入
     *
     * @return JwtTokenEnhancer
     */
//    @Bean
//    public JwtTokenEnhancer jwtTokenEnhancer() {
//        return new JwtTokenEnhancer();
//    }

    /**
     * 设置加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置clientDetail的存储方式
     * @param dataSource
     * @return
     */
    @Bean("jdbcClientDetailsService")
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(passwordEncoder());
        return clientDetailsService;
    }

//    @Bean
//    public UserDetailsService users() {
////        User.UserBuilder users = User.withDefaultPasswordEncoder();
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        manager.createUser(users.username("silentiger").password("{noop}password").roles("USER").build());
////        manager.createUser(users.username("admin").password("123456").roles("USER", "ADMIN").build());
////        return manager;
//        List<Role> roles = new ArrayList<>();
//        roles.add(new Role("SUPER_ADMIN"));
//        roles.add(new Role("USER"));
//        User user = new User("admin", "123456", roles);
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(user);
//        System.out.println(user.getPassword());
//        return manager;
//    }
}
