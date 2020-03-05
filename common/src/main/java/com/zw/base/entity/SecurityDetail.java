package com.zw.entity;

import com.zw.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
  * 用户实体类
  * @date 2020/3/3
  * @author zw
*/

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements UserDetails{
    public static final String ENABLE = "enable";

    private String username;

    private String password;

    private String sex;

    private String age;

    private String phone;

    private String status;

    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.equals(this.status, ENABLE);
    }
}
