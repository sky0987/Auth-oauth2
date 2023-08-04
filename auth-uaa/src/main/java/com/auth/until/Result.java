package com.auth.until;

import lombok.Data;

import java.io.Serializable;

@Data

public class   Result    implements Serializable {


    private  int  code;

    private   String  msg;

    private  Object  data;


    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
