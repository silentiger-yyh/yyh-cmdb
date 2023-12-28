package org.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.auth.entity.UserDetail;
import org.silentiger.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author silentiger@yyh
 * @since 2023-12-21
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select * from sys_user where username=#{username}")
    UserDetail getUserByUsername(@Param("username") String username);
}
