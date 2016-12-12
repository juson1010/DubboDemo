package com.cqx.DubboDemo.DemoForMaven.Consumers.Security;
import com.cqx.DubboDemo.DemoForMaven.Commons.IServices.UserService;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User;
import com.cqx.DubboDemo.DemoForMaven.Consumers.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by qianliang on 26/6/2016.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String account = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user;
        if (Utils.isValidMobile(account)) {
            user = userService.getUserQuery("from User user where user.mobile = ?",account);
            if (user == null) {
                throw new BadCredentialsException("此手机号码还没有注册,请先注册");
            }
        } else if (Utils.isValidEmail(account)) {
            user = userService.getUserQuery("from User user where user.email = ?",account);

            if (user == null) {
                throw new BadCredentialsException("此邮箱还没有注册,请先注册");
            }
        } else {
            throw new BadCredentialsException("无效的手机号码或邮箱");
        }

        if (user.getLocked() != null && user.getLocked()) {
            throw new BadCredentialsException("此账号已被锁定.");
        }

        if (user.getPassword() == null || user.getPassword().length() == 0) {
            throw new BadCredentialsException("您还没有设置密码");
        }

        if (!userService.verifyPassword(user, password)) {
            throw new BadCredentialsException("密码错误");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getId() + "");

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }

    public CustomUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}