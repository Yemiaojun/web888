// ProblemController.java
package com.tr.web111.controller;

import com.tr.web111.service.ProblemServiceple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import utils.Result;

@RestController
public class ProblemController {

    @Autowired
    ProblemServiceple problemService;

    @RequestMapping(value = "/addProblem", method = RequestMethod.POST)
    public String addProblem(@RequestParam("uid") int uid,
                             @RequestParam("note") String note,
                             @RequestParam("code") String code) {
        problemService.addProblem(uid, note, code);
        return Result.okGetString("添加成功");
    }

    @RequestMapping(value = "/findProblems/{uid}")
    public String findProblemsByUid(@PathVariable("uid") int uid) {
        Object problems = problemService.findProblemsByUid(uid);
        return Result.okGetStringByData("查询成功", problems);
    }
}
