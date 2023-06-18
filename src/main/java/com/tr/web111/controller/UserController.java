package com.tr.web111.controller;

import com.tr.web111.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;

@Api(value = "用户的相关接口")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    // 添加用户，将用户名和密码作为参数。如果添加成功，则返回新用户的ID
    @ApiOperation(value="添加用户", notes = "输入username和password，添加成功返回新用户的id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名字", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("password") String password){
        int newUserId = userService.addUser(username, password);
        return Result.okGetStringByData("添加成功", newUserId);
    }

    // 查找用户，将用户名和密码作为参数。如果查找成功，则返回对应的用户ID；否则，返回错误信息
    @ApiOperation(value="查找用户", notes = "输入username和password，能查找成功说明有这个用户，可登陆。用于登陆验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名字", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query", required = true)
    })
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
