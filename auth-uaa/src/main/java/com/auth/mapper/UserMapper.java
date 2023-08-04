package com.auth.mapper;

import com.auth.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {


    @Select("select   *  from  auth_user")
    List<User>  SelectAll();
}
