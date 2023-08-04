package com.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth.po.User;
import com.auth.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    @Autowired
    private UserService  userService;

    @Autowired
    private PasswordEncoder  passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //s位json数据  将json转换为对象
        System.out.println(s);
        //User user = JSON.parseObject(s, User.class);

        //从数据库查询密码
        User one = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, s));
        if (!one.getUsername().equals(s)){
            throw   new RuntimeException("账号或密码错误");
        }
        return   getUserAuthority(s);
    }

    public UserDetails getUserAuthority(String s){
        //返回拥有角色和权限
        String[] authority = {"user:del"};
        //user.setPassword("");
        //将对象转换为json
        //String s = JSON.toJSONString(user);
        //权限（资源）
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(s).password("").authorities(authority).build();
        return userDetails;

    }
}
