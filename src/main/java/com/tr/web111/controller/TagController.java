// TagController.java
package com.tr.web111.controller;

import com.tr.web111.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;

import java.util.Date;
import java.util.List;

@RestController
public class TagController {

    @Autowired
    TagService tagService;

    // 根据标签查找问题
    @RequestMapping(value = "/findProblemsByTag",method = RequestMethod.POST)
    public String findProblemsByTag(@RequestParam(value = "type", required = false) Integer type,
                                    @RequestParam(value = "cateID", required = false) Integer cateID,
                                    @RequestParam(value = "level", required = false) Integer level,
                                    @RequestParam(value = "exp", required = false) Integer exp,
                                    @RequestParam(value = "finish", required = false) Boolean finish,
                                    @RequestParam(value = "editTime", required = false) Date editTime,
                                    @RequestParam(value = "posID", required = false) Integer posID,
                                    @RequestParam(value = "did", required = false) Integer did) {
        Object problemPids = tagService.findProblemsByTag(type, cateID, level, exp, finish, editTime, posID, did);
        return Result.okGetStringByData("查询成功", problemPids);
    }

    // 更新标签属性，包括 type、cateID、level、exp、finish、editTime、posID 和 did
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
                break;
            case "posID":
                tagService.updatePosID(pid, Integer.parseInt(newValue));
                break;
            case "did":
                tagService.updateDid(pid, Integer.parseInt(newValue));
                break;
            default:
                return Result.errorGetString("未知的属性名：" + propertyName);
        }
        return Result.okGetString("更新成功");
    }
}
