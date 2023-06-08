package com.tr.web111.controller;

import com.tr.web111.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    // 添加用户，将用户名和密码作为参数。如果添加成功，则返回新用户的ID
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("password") String password){
        int newUserId = userService.addUser(username, password);
        return Result.okGetStringByData("添加成功", newUserId);
    }

    // 查找用户，将用户名和密码作为参数。如果查找成功，则返回对应的用户ID；否则，返回错误信息
    @RequestMapping(value = "/findUser",method = RequestMethod.POST)
    public String findUser(@RequestParam("username") String username,
                           @RequestParam("password") String password){
        int foundUserId = userService.findUser(username, password);
        if (foundUserId != -1) {
            return Result.okGetStringByData("登录成功", foundUserId);
        } else {
            return Result.errorGetString("用户名或密码错误");
        }
    }

}
