// ProblemController.java
package com.tr.web111.controller;

import com.tr.web111.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import utils.Result;

@RestController
public class ProblemController {

    @Autowired
    ProblemService problemService;

    @RequestMapping(value = "/addProblem", method = RequestMethod.POST)
    public String addProblem(@RequestParam("uid") int uid) {
        problemService.addProblem(uid);
        return Result.okGetString("Problem added successfully");
    }

    @RequestMapping(value = "/findProblems/{uid}")
    public String findProblemsByUid(@PathVariable("uid") int uid) {
        Object problems = problemService.findProblemsByUid(uid);
        return Result.okGetStringByData("Problems retrieved successfully", problems);
    }

    @RequestMapping(value = "/findProblem/{pid}")
    public String findProblemByPid(@PathVariable("pid") int pid) {
        Object problem = problemService.findProblemByPid(pid);
        return Result.okGetStringByData("Problem retrieved successfully", problem);
    }

    @RequestMapping(value = "/findProblemsByPidList", method = RequestMethod.POST)
    public String findProblemsByPidList(@RequestBody List<Integer> pidList) {
        Object problems = problemService.findProblemsByPidList(pidList);
        return Result.okGetStringByData("Problems retrieved successfully", problems);
    }

    @RequestMapping(value = "/updateNote", method = RequestMethod.POST)
    public String updateNote(@RequestParam("pid") int pid,
                             @RequestParam("note") String note) {
        problemService.updateNote(pid, note);
        return Result.okGetString("Note updated successfully");
    }

    @RequestMapping(value = "/updateCode", method = RequestMethod.POST)
    public String updateCode(@RequestParam("pid") int pid,
                             @RequestParam("code") String code) {
        problemService.updateCode(pid, code);
        return Result.okGetString("Code updated successfully");
    }

    @RequestMapping(value = "/updateDescription", method = RequestMethod.POST)
    public String updateDescription(@RequestParam("pid") int pid,
                                    @RequestParam("description") String description) {
        problemService.updateDescription(pid, description);
        return Result.okGetString("Description updated successfully");
    }

    @RequestMapping(value = "/updateTitle", method = RequestMethod.POST)
    public String updateTitle(@RequestParam("pid") int pid,
                              @RequestParam("title") String title) {
        problemService.updateTitle(pid, title);
        return Result.okGetString("Title updated successfully");
    }
}
