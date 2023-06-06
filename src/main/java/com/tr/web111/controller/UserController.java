package com.tr.web111.controller;

import com.tr.web111.service.UserServiceple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;

@RestController
public class UserController {

    @Autowired
    UserServiceple userService;

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("password") String password){
        userService.addUser(username,password);
        return Result.okGetString("添加成功");
    }

    @RequestMapping(value = "/findUser/{username}")
    public String findUserByUserName(@PathVariable("username") String username){
        Object pojo=userService.findUserByName(username);
        return Result.okGetStringByData("查询成功",pojo);
    }

}
