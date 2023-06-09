// ProblemController.java
package com.tr.web111.controller;

import com.tr.web111.service.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import utils.Result;

@Api(value = "题目的相关接口")
@RestController
public class ProblemController {

    @Autowired
    ProblemService problemService;

    @ApiOperation(value="添加一道题目", notes = "根据uid添加一道题目")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true)
    )
    @RequestMapping(value = "/addProblem", method = RequestMethod.POST)
    public String addProblem(@RequestParam("uid") int uid) {
        problemService.addProblem(uid);
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

    @ApiOperation(value="查看一个题目", notes = "根据pid搜索一道题目")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "pid", value = "题目id", dataType = "int", paramType = "path", required = true)
    )
    @RequestMapping(value = "/findProblem/{pid}", method = RequestMethod.GET)
    public String findProblemByPid(@PathVariable("pid") int pid) {
        Object problem = problemService.findProblemByPid(pid);
        return Result.okGetStringByData("问题成功检索", problem);
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
    public String updateNote(@RequestParam("pid") int pid,
                             @RequestParam("note") String note) {
        problemService.updateNote(pid, note);
        return Result.okGetString("笔记成功更新");
    }

    @ApiOperation(value="更新题目代码", notes = "根据pid和code更新题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "题目id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "code", value = "代码内容", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/updateCode", method = RequestMethod.POST)
    public String updateCode(@RequestParam("pid") int pid,
                             @RequestParam("code") String code) {
        problemService.updateCode(pid, code);
        return Result.okGetString("代码成功更新");
    }

    @ApiOperation(value="更新题目描述", notes = "根据pid和description更新题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "题目id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "description", value = "描述内容", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/updateDescription", method = RequestMethod.POST)
    public String updateDescription(@RequestParam("pid") int pid,
                                    @RequestParam("description") String description) {
        problemService.updateDescription(pid, description);
        return Result.okGetString("描述成功更新");
    }

    @ApiOperation(value="更新题目标题", notes = "根据pid和title更新题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "题目id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/updateTitle", method = RequestMethod.POST)
    public String updateTitle(@RequestParam("pid") int pid,
                              @RequestParam("title") String title) {
        problemService.updateTitle(pid, title);
        return Result.okGetString("标题成功更新");
    }
}
