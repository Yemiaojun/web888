// TagController.java
package com.tr.web111.controller;

import com.tr.web111.dto.ProblemTagStringDto;
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
import java.util.Map;

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
                                    @RequestParam(value = "editTime", required = false) String editTimeStr,
                                    @RequestParam(value = "posID", required = false) Integer posID,
                                    @RequestParam(value = "did", required = false) Integer did) {
        List<TagPojo> problemPids = tagService.findProblemsByTag(type, cateID, level, exp, finish, editTimeStr, posID, did);
        return Result.okGetStringByData("查询成功", problemPids);
    }

    @ApiOperation(value = "根据标签查找问题", notes = "根据指定的标签查找问题，返回问题及其对应的标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户ID", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "cateID", value = "类别ID", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "level", value = "难度", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "exp", value = "掌握程度", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "finish", value = "完成情况", dataType = "Boolean", paramType = "query", required = false),
            @ApiImplicitParam(name = "editTime", value = "编辑时间", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "posID", value = "职位ID", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "did", value = "部门ID", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query", required = false)
    })
    @RequestMapping(value = "/findProblemTagDtosByTag", method = RequestMethod.GET)
    public String findProblemTagDtosByTag(@RequestParam(value = "uid", required = true) Integer uid,
                                          @RequestParam(value = "title", required = true) String title,
                                          @RequestParam(value = "type", required = false) Integer type,
                                          @RequestParam(value = "cateID", required = false) Integer cateID,
                                          @RequestParam(value = "level", required = false) Integer level,
                                          @RequestParam(value = "exp", required = false) Integer exp,
                                          @RequestParam(value = "finish", required = false) Boolean finish,
                                          @RequestParam(value = "editTime", required = false) String editTimeStr,
                                          @RequestParam(value = "posID", required = false) Integer posID,
                                          @RequestParam(value = "did", required = false) Integer did,
                                          @RequestParam(value = "sort", required = false) String sort) {
        if(editTimeStr==null)editTimeStr="0";
        List<ProblemTagStringDto> problemTagDtos = tagService.findProblemTagStringDtosByTag(uid, title, type, cateID, level, exp, finish, editTimeStr, posID, did, sort);
        return Result.okGetStringByData("题目和对应标签成功检索", problemTagDtos);
    }



    @ApiOperation(value = "根据标签查找问题", notes = "根据指定的标签查找问题，返回问题及其对应的标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户ID", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pid", value = "问题ID", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "cateID", value = "类别ID", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "level", value = "级别", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "exp", value = "经验", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "finish", value = "完成情况", dataType = "Boolean", paramType = "query", required = false),
            @ApiImplicitParam(name = "editTime", value = "编辑时间", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "posID", value = "职位ID", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(name = "did", value = "部门ID", dataType = "Integer", paramType = "query", required = false)
    })
    @RequestMapping(value = "/findDtosByTagPid", method = RequestMethod.GET)
    public String findDtosByTagPid(@RequestParam(value = "uid", required = true) Integer uid,
                                   @RequestParam(value = "pid", required = true) Integer pid,
                                   @RequestParam(value = "type", required = false) Integer type,
                                   @RequestParam(value = "cateID", required = false) Integer cateID,
                                   @RequestParam(value = "level", required = false) Integer level,
                                   @RequestParam(value = "exp", required = false) Integer exp,
                                   @RequestParam(value = "finish", required = false) Boolean finish,
                                   @RequestParam(value = "editTime", required = false) String editTimeStr,
                                   @RequestParam(value = "posID", required = false) Integer posID,
                                   @RequestParam(value = "did", required = false) Integer did) {
        ProblemTagStringDto problemTagDtos = tagService.findDtoByTagUid(uid, pid, type, cateID, level, exp, finish, editTimeStr, posID, did);
        return Result.okGetStringByData("题目和对应标签成功检索", problemTagDtos);
    }




    // 更新标签属性，包括 type、cateID、level、exp、finish、editTime、posID 和 did
    @ApiOperation(value="更新标签属性", notes = "根据pid、propertyName和newValue更新一道题目有的标签或给题目添加一个标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "题目id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "propertyName", value = "标签的名字", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "newValue", value = "该标签的值", dataType = "Integer", paramType = "query", required = true)
    })
    @RequestMapping(value = "/updateTag",method = RequestMethod.POST)
    public String updateTag(@RequestBody Map<String,String> tag) {
        switch ((String) tag.get("propertyName")) {
            case "type":
                tagService.updateType(Integer.valueOf(tag.get("pid")) , Integer.parseInt(tag.get("newValue")));
                break;
            case "cateID":
                tagService.updateCateID(Integer.valueOf(tag.get("pid")), Integer.parseInt(tag.get("newValue")));
                break;
            case "level":
                tagService.updateLevel(Integer.valueOf(tag.get("pid")), Integer.parseInt(tag.get("newValue")));
                break;
            case "exp":
                tagService.updateExp(Integer.valueOf(tag.get("pid")), Integer.parseInt(tag.get("newValue")));
                break;
            case "finish":
                tagService.updateFinish(Integer.valueOf(tag.get("pid")), Boolean.parseBoolean(tag.get("newValue")));
                break;
            case "editTime":
                // 我们假设 newValue 是毫秒级的时间戳
                tagService.updateEditTime(Integer.valueOf(tag.get("pid")), new Date(Long.parseLong(tag.get("newValue"))));
                break;//不一定有用，但放着就放着吧
            case "posID":
                tagService.updatePosID(Integer.valueOf(tag.get("pid")), Integer.parseInt(tag.get("newValue")));
                break;
            case "did":
                tagService.updateDid(Integer.valueOf(tag.get("pid")), Integer.parseInt(tag.get("newValue")));
                break;
            case "cid":
                tagService.updateCid(Integer.valueOf(tag.get("pid")), Integer.parseInt(tag.get("newValue")));
                break;
            default:
                return Result.errorGetString("未知的属性名：" + (String) tag.get("propertyName"));
        }
        return Result.okGetString("更新成功");
    }
    

    @ApiImplicitParam(name = "pid", value = "问题ID", dataType = "int", paramType = "query", required = true)
    @ApiOperation(value = "获取问题的编辑时间", notes = "根据问题ID(pid)获取编辑时间，并返回为“近一周”，“近一个月”，“近半年”等描述")
    @RequestMapping(value = "/timeSinceLastEdit/{pid}", method = RequestMethod.GET)
    public String timeSinceLastEdit(@PathVariable("pid") int pid) {
        String timeDescription = tagService.timeSinceLastEdit(pid);
        return Result.okGetStringByData("查询成功", timeDescription);
    }

}
