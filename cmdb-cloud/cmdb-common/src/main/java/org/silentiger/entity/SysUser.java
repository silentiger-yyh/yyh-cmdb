package org.silentiger.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author silentiger@yyh
 * @since 2023-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 头像
     */
    @TableField("icon")
    private String icon;

    /**
     * 用户名（账号）
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 备注信息
     */
    @TableField("note")
    private String note;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    private Date modifyTime;

    /**
     * 账号状态：1-启用，0-禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 最后一次登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;
}
