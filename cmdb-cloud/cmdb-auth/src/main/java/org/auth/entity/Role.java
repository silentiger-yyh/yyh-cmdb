package org.auth.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.silentiger.entity.SysRole;
import org.springframework.security.core.GrantedAuthority;

@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends SysRole implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return getRoleName();
    }
}
