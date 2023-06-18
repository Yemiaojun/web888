// ProblemController.java
package com.tr.web111.controller;

import com.tr.web111.dto.ProblemTagDto;
import com.tr.web111.dto.ProblemTagStringDto;
import com.tr.web111.service.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import utils.Result;

@Api(value = "题目的相关接口")
@RestController
public class ProblemController {

    @Autowired
    ProblemService problemService;

    @ApiOperation(value="添加一道题目", notes = "根据uid添加一道题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "int", paramType = "query", required = true)
    })
    @RequestMapping(value = "/addProblem", method = RequestMethod.POST)
    public String addProblem(@RequestBody Map<String,Object> problem) {
        problemService.addProblem(Integer.valueOf((String) problem.get("uid")) , (String) problem.get("title"), (String) problem.get("description"), Integer.valueOf((String) problem.get("type")), Integer.valueOf((String) problem.get("level")), Integer.valueOf((String) problem.get("cateID")), Integer.valueOf((String) problem.get("did")), Integer.valueOf((String) problem.get("posID")));
        return Result.okGetString("问题成功添加");
    }

    @ApiOperation(value="查找用户所有的题目", notes = "根据uid搜索所有题目")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "path", required = true)
    )
    @RequestMapping(value = "/findProblems/{uid}", method = RequestMethod.GET)
    public String findProblemsByUid(@PathVariable("uid") int uid) {
        Object problems = problemService.findProblemsByUid(uid);
        return Result.okGetStringByData("问题成功检索", problems);
    }

    @ApiOperation(value="查找用户所有的题目及相关标签", notes = "根据uid搜索所有题目以及对应的标签")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "path", required = true)
    )
    @RequestMapping(value = "/findProblemTagDtos/{uid}", method = RequestMethod.GET)
    public String findProblemTagDtosByUid(@PathVariable("uid") int uid) {
        List<ProblemTagDto> problemTagDtos = problemService.findProblemTagDtosByUid(uid);
        return Result.okGetStringByData("题目和对应标签成功检索", problemTagDtos);
    }

    @ApiOperation(value="查找用户所有的题目及相关标签，并将标签ID转为名称", notes = "根据uid搜索所有题目以及对应的标签，并将标签ID转为名称")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "path", required = true)
    )
    @RequestMapping(value = "/findProblemTagStringDtos/{uid}", method = RequestMethod.GET)
    public String findProblemTagStringDtosByUid(@PathVariable("uid") int uid) {
        List<ProblemTagStringDto> problemTagStringDtos = problemService.findProblemTagStringDtosByUid(uid);
        return Result.okGetStringByData("题目和对应标签成功检索，并已将ID转为名称", problemTagStringDtos);
    }


    @ApiOperation(value="查看一个题目", notes = "根据pid搜索一道题目")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "pid", value = "题目id", dataType = "int", paramType = "path", required = true)
    )
    @RequestMapping(value = "/findProblem/{pid}", method = RequestMethod.GET)
    public String findProblemByPid(@PathVariable("pid") int pid) {
        Object problem = problemService.findProblemByPid(pid);
        return Result.okGetStringByData("问题成功检索", problem);
    }

    @ApiOperation(value="查看一个题目", notes = "根据title模糊搜索一道题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String", paramType = "path", required = true),
    })
    @RequestMapping(value = "/findProblemsByTitle/{uid}/{title}", method = RequestMethod.GET)
    public String findProblemsByUidAndTitle(@PathVariable("uid") int uid,
                                            @PathVariable("title") String title) {
        List<ProblemTagStringDto> problemTagStringDtos = problemService.findProblemsByUidAndTitle(uid, title);
        return Result.okGetStringByData("成功获取公司信息", problemTagStringDtos);
    }

    @RequestMapping(value = "/findProblemsByPidList", method = RequestMethod.POST)
    public String findProblemsByPidList(@RequestBody List<Integer> pidList) {
        Object problems = problemService.findProblemsByPidList(pidList);
        return Result.okGetStringByData("问题成功检索", problems);
    }

    @ApiOperation(value="更新题目笔记", notes = "根据pid和note更新题目，这里note要支持多媒体格式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "题目id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "note", value = "笔记内容", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/updateNote", method = RequestMethod.POST)
    public String updateNote(@RequestBody Map<String,Object> prob) {
        problemService.updateNote((Integer) prob.get("pid"), (String) prob.get("note"));
        return Result.okGetString("笔记成功更新");
    }

    @ApiOperation(value="更新题目代码", notes = "根据pid和code更新题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "题目id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "code", value = "代码内容", dataType = "String", paramType = "body", required = true)
    })
    @RequestMapping(value = "/updateCode", method = RequestMethod.POST)
    public String updateCode(@RequestBody Map<String,Object> prob) {
        problemService.updateCode((Integer) prob.get("pid"), (String) prob.get("code"));
        return Result.okGetString("代码成功更新");
    }


    @ApiOperation(value="更新题目描述", notes = "根据pid和description更新题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "题目id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "description", value = "描述内容", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/updateDescription", method = RequestMethod.POST)
    public String updateDescription(@RequestBody Map<String,Object> prob) {
        problemService.updateDescription((Integer) prob.get("pid"), (String) prob.get("description"));
        return Result.okGetString("描述成功更新");
    }

    @ApiOperation(value="更新题目标题", notes = "根据pid和title更新题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "题目id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/updateTitle", method = RequestMethod.POST)
    public String updateTitle(@RequestBody Map<String,Object> prob) {
        problemService.updateTitle((Integer) prob.get("pid"), (String) prob.get("title"));
        return Result.okGetString("标题成功更新");
    }

    @ApiOperation(value="查找用户所有的题目及相关标签并按等级排序", notes = "根据uid搜索所有题目以及对应的标签，并按等级排序")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "path", required = true)
    )
    @RequestMapping(value = "/findProblemTagDtosSortByLevel/{uid}", method = RequestMethod.GET)
    public String findProblemTagDtosByUidSortByLevel(@PathVariable("uid") int uid) {
        List<ProblemTagStringDto> problemTagDtos = problemService.findProblemTagStringDtosByUidSortByLevel(uid);
        return Result.okGetStringByData("题目和对应标签成功检索并按等级排序", problemTagDtos);
    }

    @ApiOperation(value="查找用户所有的题目及相关标签并按经验排序", notes = "根据uid搜索所有题目以及对应的标签，并按经验排序")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "path", required = true)
    )
    @RequestMapping(value = "/findProblemTagDtosSortByExp/{uid}", method = RequestMethod.GET)
    public String findProblemTagDtosByUidSortByExp(@PathVariable("uid") int uid) {
        List<ProblemTagStringDto> problemTagDtos = problemService.findProblemTagStringDtosByUidSortByExp(uid);
        return Result.okGetStringByData("题目和对应标签成功检索并按经验排序", problemTagDtos);
    }

    @ApiOperation(value="查找用户所有的题目及相关标签并按添加时间排序", notes = "根据uid搜索所有题目以及对应的标签，并按添加时间排序")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "path", required = true)
    )
    @RequestMapping(value = "/findProblemTagDtosSortByAddTime/{uid}", method = RequestMethod.GET)
    public String findProblemTagDtosByUidSortByAddTime(@PathVariable("uid") int uid) {
        List<ProblemTagStringDto> problemTagDtos = problemService.findProblemTagStringDtosByUidSortByAddTime(uid);
        return Result.okGetStringByData("题目和对应标签成功检索并按添加时间排序", problemTagDtos);
    }

}
