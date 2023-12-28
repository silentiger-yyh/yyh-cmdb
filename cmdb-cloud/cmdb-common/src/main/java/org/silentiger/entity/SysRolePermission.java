package org.silentiger.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author silentiger@yyh
 * @since 2023-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_permission")
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 该角色拥有权限的菜单ID
     */
    @TableField("menu_id")
    private Long menuId;


}
