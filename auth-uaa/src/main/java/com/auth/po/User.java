package com.auth.po;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 用户个人信息
 */
@Data
@TableName("auth_user")
public class User   implements Serializable {


    private static final long serialVersionUID = 1L;
    private  Long  id;

    private  String username;

    private  String  password;

    private   int  status;

    private LocalDateTime  createTime;

    private  LocalDateTime  updateTime;

    /**
     * 操作者
     */
    private  String  operator;
}
