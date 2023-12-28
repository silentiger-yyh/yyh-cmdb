package org.auth.service.impl;

import org.auth.entity.UserDetail;
import org.auth.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 密码模式需要的UserDetailsService
 *
 * @Author silentiger@yyh
 * @Date 2023-12-21 14:49:29
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public UserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        return sysUserService.loadUserByUsername(username);
    }
}
