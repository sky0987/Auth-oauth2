package com.auth.service.impl;

import com.auth.mapper.UserMapper;
import com.auth.po.User;
import com.auth.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService {

}
