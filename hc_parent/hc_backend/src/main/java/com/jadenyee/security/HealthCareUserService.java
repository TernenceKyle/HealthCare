package com.jadenyee.security;

import com.jadenyee.pojo.Permission;
import com.jadenyee.pojo.Role;
import com.jadenyee.pojo.User;
import com.jadenyee.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class HealthCareUserService implements UserDetailsService {
    @Reference
    private UserService service;
    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * 使用 SpringSecurity 实现后台管理系统用户登录的权限验证
     *
     * @param username 登录的用户名
     * @return 返回验证后的用户信息z
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User loginUser = service.getUserByUsername(username);
        if (loginUser == null) {
            return null;
        }
        System.out.println(encoder.encode("admin"));
        System.out.println(encoder.encode("1234"));
        String password = loginUser.getPassword();
        List<GrantedAuthority> authenticationList = new ArrayList<>();
        Set<Role> roles = loginUser.getRoles();
        for (Role role : roles) {
            authenticationList.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                authenticationList.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        return new org.springframework.security.core.userdetails.User(username, password, authenticationList);
    }
}
