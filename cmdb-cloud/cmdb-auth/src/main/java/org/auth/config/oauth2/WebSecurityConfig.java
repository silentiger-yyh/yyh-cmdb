package org.auth.config.oauth2;
//

//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * SpringSecurity相关配置
// * Created by macro on 2020/6/19.
// */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig{
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}
//


import org.auth.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurity配置文件,支持password模式要配置AuthenticationManager
 * @Author silentiger@yyh
 * @Date 2023-12-17 13:52:58
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;
    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            // 过滤请求
            .authorizeRequests()
            // 对于登录login 验证码captchaImage 允许匿名访问
            .antMatchers("/login", "/captchaImage").permitAll()
            .antMatchers("/user").permitAll()
            .antMatchers(
                    HttpMethod.GET,
                    "/*.html",
                    "/**/*.html",
                    "/**/*.css",
                    "/**/*.js"
            ).permitAll()
            .antMatchers("/swagger-resources/**").anonymous()
            .antMatchers("/*/api-docs").anonymous()
            .and()
            .requestMatchers()
            .anyRequest()
            .and()
            .formLogin()
            .loginPage("/login")  // 自定义登陆页面
            .and()
            .httpBasic()  // 这里不配置的话默认采用Basic方式，base64，不安全，后面需要修改为更加安全的方式
            .and()
            .csrf().disable()
            .cors().disable();
    }
    /**
     * 密码授权模式用到的AuthenticationManager类
     * @return AuthenticationManager
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 前端传递密码时采用明文传输，这里配置将密码进行加密，与数据库中的密码对比
     * 数据库中的密码按照BCrypt加密存储，存储的密码不能加{id}前缀
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
//    public static void main(String[] args) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        // 加密
//        String encodedPassword = passwordEncoder.encode("silentiger");
////        String encodedPassword = passwordEncoder.encode("123456");
//        System.out.println(encodedPassword);
//    }
}