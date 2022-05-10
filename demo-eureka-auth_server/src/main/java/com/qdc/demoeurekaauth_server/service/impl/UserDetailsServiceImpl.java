package com.qdc.demoeurekaauth_server.service.impl;

import com.qdc.demoeurekaauth_server.pojo.Role;
import com.qdc.demoeurekaauth_server.pojo.User;
import com.qdc.demoeurekaauth_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//UserDetailsService密码授权模式，用它做权限判定
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;
//根据用户名查用户
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userService.getUser(username);
        if(user==null || user.getId()<1){
            throw new UsernameNotFoundException("user not found"+username);
        }
//        将User类型转换为UserDetails类型
//        权限验证
//        无法显示导入语句，故使用类名
//        7个参数含义分别为：用户名、密码、已启用账户、账户是否过期、验证是否过期、账户是否被锁定、登录用户权限集合
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
                true, true,true,true,getGrantedAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(User user) {
        Set<Role> roles=new HashSet<>();
        Role r=new Role(1,"admin");
        roles.add(r);
        user.setRoles(roles);

        Set<GrantedAuthority> authorities=new HashSet<>();
        for(Role role:user.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE"+role.getName()));
        }
        return authorities;
    }
}