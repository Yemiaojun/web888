package com.tr.web111.controller;

import com.tr.web111.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;

import java.util.Map;

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
    public String addUser(@RequestBody Map<String,Object> user){
        int newUserId = userService.addUser((String) user.get("username"), (String) user.get("password"));
        return Result.okGetStringByData("添加成功", newUserId);
    }

    // 查找用户，将用户名和密码作为参数。如果查找成功，则返回对应的用户ID；否则，返回错误信息
    @ApiOperation(value="查找用户", notes = "输入username和password，能查找成功说明有这个用户，可登陆。用于登陆验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名字", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/findUser",method = RequestMethod.POST)
    public String findUser(@RequestBody Map<String,Object> user){
        int foundUserId = userService.findUser((String) user.get("username"), (String) user.get("password"));
        if (foundUserId != -1) {
            return Result.okGetStringByData("登录成功", foundUserId);
        } else {
            return Result.errorGetString("用户名或密码错误");
        }
    }

    @ApiOperation(value="修改密码", notes = "输入uid、password和newPassword，如果uid和password匹配，就修改密码为newPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户ID", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "原密码", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "newPassword", value = "新密码", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    public String changePassword(@RequestBody Map<String,Object> user) {
        int userId = (Integer) user.get("uid");
        String password = (String) user.get("password");
        String newPassword = (String) user.get("newPassword");
        int resultUid = userService.changePassword(userId, password, newPassword);
        if (resultUid != -1) {
            return Result.okGetStringByData("密码修改成功", resultUid);
        } else {
            return Result.errorGetString("用户ID或原密码错误");
        }
    }


}
