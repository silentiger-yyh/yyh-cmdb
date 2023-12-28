package org.auth.entity;

/**
 * 用户信息
 *
 * @Author silentiger@yyh
 * @Date 2023-12-21 09:43:27
 */

import lombok.Data;
import org.silentiger.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDetail extends SysUser implements Serializable, UserDetails {
    private List<Role> authorities;

    /**
     * 用户账号是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户账号是否被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户密码是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


//    默认使用恒等去判断是否是同一个对象，因为登录的同一个用户，如果再次登录就会封装
//    一个新的对象，这样会导致登录的用户永远不会相等，所以需要重写equals方法
    @Override
    public boolean equals(Object obj) {
        //会话并发生效，使用username判断是否是同一个用户

        if (obj instanceof UserDetail){
            //字符串的equals方法是已经重写过的
            return ((UserDetail) obj).getUsername().equals(this.getUsername());
        }else {
            return false;
        }
    }

}