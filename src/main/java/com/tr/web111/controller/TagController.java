// TagController.java
package com.tr.web111.controller;

import com.tr.web111.pojo.TagPojo;
import com.tr.web111.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;

import java.util.Date;
import java.util.List;

@Api(value = "标签的相关接口")
@RestController
public class TagController {

    @Autowired
    TagService tagService;

    // 根据标签查找问题
    @ApiOperation(value="根据标签查找题目", notes = "根据标签{type,cateID,level,exp,finish,editTime,posID,did}" +
            "搜索题目，可选择一个或多个标签，非强制要求所有标签都要传参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "cateID", value = "类别id", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "level", value = "难度", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "exp", value = "掌握程度", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "finish", value = "完成情况", dataType = "Boolean", paramType = "query", required = true),
            @ApiImplicitParam(name = "editTime", value = "编辑时间", dataType = "Date", paramType = "query", required = true),
            @ApiImplicitParam(name = "posID", value = "岗位id", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "did", value = "部门id", dataType = "Integer", paramType = "query", required = true)
    })
    @RequestMapping(value = "/findProblemsByTag",method = RequestMethod.POST)
    public String findProblemsByTag(@RequestParam(value = "type", required = false) Integer type,
                                    @RequestParam(value = "cateID", required = false) Integer cateID,
                                    @RequestParam(value = "level", required = false) Integer level,
                                    @RequestParam(value = "exp", required = false) Integer exp,
                                    @RequestParam(value = "finish", required = false) Boolean finish,
                                    @RequestParam(value = "editTime", required = false) Date editTime,
                                    @RequestParam(value = "posID", required = false) Integer posID,
                                    @RequestParam(value = "did", required = false) Integer did) {
        List<TagPojo> problemPids = tagService.findProblemsByTag(type, cateID, level, exp, finish, editTime, posID, did);
        return Result.okGetStringByData("查询成功", problemPids);
    }

    // 更新标签属性，包括 type、cateID、level、exp、finish、editTime、posID 和 did
    @ApiOperation(value="更新标签属性", notes = "根据pid、propertyName和newValue更新一道题目有的标签或给题目添加一个标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "题目id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "propertyName", value = "标签的名字", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "newValue", value = "该标签的值", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/updateTag",method = RequestMethod.POST)
    public String updateTag(@RequestParam("pid") int pid,
                            @RequestParam("propertyName") String propertyName,
                            @RequestParam("newValue") String newValue) {
        switch (propertyName) {
            case "type":
                tagService.updateType(pid, Integer.parseInt(newValue));
                break;
            case "cateID":
                tagService.updateCateID(pid, Integer.parseInt(newValue));
                break;
            case "level":
                tagService.updateLevel(pid, Integer.parseInt(newValue));
                break;
            case "exp":
                tagService.updateExp(pid, Integer.parseInt(newValue));
                break;
            case "finish":
                tagService.updateFinish(pid, Boolean.parseBoolean(newValue));
                break;
            case "editTime":
                // 我们假设 newValue 是毫秒级的时间戳
                tagService.updateEditTime(pid, new Date(Long.parseLong(newValue)));
                break;//不一定有用，但放着就放着吧
            case "posID":
                tagService.updatePosID(pid, Integer.parseInt(newValue));
                break;
            case "did":
                tagService.updateDid(pid, Integer.parseInt(newValue));
                break;
            case "cid":
                tagService.updateCid(pid, Integer.parseInt(newValue));
                break;
            default:
                return Result.errorGetString("未知的属性名：" + propertyName);
        }
        return Result.okGetString("更新成功");
    }
    

    @ApiImplicitParam(name = "pid", value = "问题ID", dataType = "int", paramType = "query", required = true)
    @ApiOperation(value = "获取问题的编辑时间", notes = "根据问题ID(pid)获取编辑时间，并返回为“近一周”，“近一个月”，“近半年”等描述")
    @RequestMapping(value = "/timeSinceLastEdit", method = RequestMethod.GET)
    public String timeSinceLastEdit(@RequestParam("pid") int pid) {
        String timeDescription = tagService.timeSinceLastEdit(pid);
        return Result.okGetStringByData("查询成功", timeDescription);
    }

}
