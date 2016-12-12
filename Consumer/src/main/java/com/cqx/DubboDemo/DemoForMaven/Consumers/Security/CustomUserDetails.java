package com.cqx.DubboDemo.DemoForMaven.Consumers.Security;

import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User_Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by qianliang on 26/6/2016.
 */
public class CustomUserDetails implements UserDetails {

    private User user;
    private List<User_Role> userRoles;
    private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    public CustomUserDetails(){

        System.out.println("run custome ");
    }
    public CustomUserDetails(User user, List<User_Role> userRoles) {
        this.user = user;
        this.userRoles = userRoles;
        for (User_Role userRole : userRoles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRoleId()));
        }
    }

    public User getUser() {
        return user;
    }

    public List<User_Role> getUserRoles() {
        return userRoles;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {   return user.getName();}

    public boolean isAccountNonExpired() {
        return false;
    }

    public boolean isAccountNonLocked() {
        return user.getLocked() == null || !user.getLocked();
    }

    public boolean isCredentialsNonExpired() {
        return false;
    }

    public boolean isEnabled() {
        return true;
    }
}
