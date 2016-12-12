package com.cqx.DubboDemo.DemoForMaven.Consumers.Security;
import com.cqx.DubboDemo.DemoForMaven.Commons.IServices.UserService;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User_Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qianliang on 26/6/2016.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserById(Integer.parseInt(username));
        List<User_Role> userRoles = userService.getUserRoles(Integer.parseInt(username));

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with userId '%s'.", username));
        } else {
            return new CustomUserDetails(user, userRoles);
        }
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
