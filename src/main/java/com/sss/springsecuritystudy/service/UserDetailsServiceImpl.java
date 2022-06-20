package com.sss.springsecuritystudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author lzb
 * create time: 2022/6/20
 * UserDetailsService 实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder pw;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.查询数据库判断用户是否存在，如果不存在就会抛出UsernameNotFoundException异常
        if(!"admin".equals(username)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 2.把查询出来的密码（注册时已经加密过）进行解析，或者直接把密码放入构造方法
        String password = pw.encode("123");
        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_abc,/main.html"));
    }
}
