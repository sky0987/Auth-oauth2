package com.auth.controller;


import com.auth.until.Code;
import com.auth.until.Result;
import com.auth.po.User;
import com.auth.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;




    @GetMapping("/user/page")
    public Result UserAll(int page, int pagesize, String name) {

        Page InfoPage = new Page<>(page, pagesize);

        Page page1 = userService.page(InfoPage, new LambdaQueryWrapper<User>().
                like(StringUtils.isNotEmpty(name), User::getUsername, name).
                orderByDesc(User::getCreateTime));

        return new Result(page1 != null ? Code.GET_OK : Code.GET_ERR,
                page1 != null ? Code.MSG_GET_OK : Code.MSG_GET_ERR,
                InfoPage);
    }


    @PostMapping("/user/save")
    public Result SaveUser(@RequestBody User user) {
        boolean save = userService.save(user);
        return new Result(save == true ? Code.SAVE_OK : Code.SAVE_ERR,
                save == true ? Code.MSG_SAVE_OK : Code.MSG_SAVE_ERR
        );
    }


    @RequestMapping("/login-success")
    public Result test() {
        return new Result(200, "成功");

    }


    @PostMapping("/oauth/token")
    public  void log(@RequestParam String client_id,@RequestParam String client_secret,
                         @RequestParam String grant_type,@RequestParam String username,
                         @RequestParam  String password){

    }


}