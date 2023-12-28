package org.auth.service;

import org.auth.entity.UserDetail;
import org.silentiger.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author silentiger@yyh
 * @since 2023-12-21
 */

public interface ISysUserService extends IService<SysUser> {
    UserDetail loadUserByUsername(String username);
}
