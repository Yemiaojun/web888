// PositionController.java
package com.tr.web111.controller;

import com.tr.web111.pojo.PositionPojo;
import com.tr.web111.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import utils.Result;

@RestController
public class PositionController {

    @Autowired
    PositionService positionService;

    // 查找用户的所有position
    @RequestMapping(value = "/findPositions", method = RequestMethod.GET)
    public String findAllPositionsByUid(@RequestParam("uid") int uid) {
        List<PositionPojo> positions = positionService.findAllPositionsByUid(uid);
        return Result.okGetStringByData("成功获取职位信息", positions);
    }

    // 查找名字（字符串匹配查找）
    @RequestMapping(value = "/findPositionsByName", method = RequestMethod.GET)
    public String findPositionsByUidAndPosName(@RequestParam("uid") int uid,
                                               @RequestParam("posName") String posName) {
        List<PositionPojo> positions = positionService.findPositionsByUidAndPosName(uid, posName);
        return Result.okGetStringByData("成功获取职位信息", positions);
    }

    // 修改一条position
    @RequestMapping(value = "/updatePosition", method = RequestMethod.POST)
    public String updatePosition(@RequestParam("posId") int posId,
                                 @RequestParam("newPosName") String newPosName) {
        positionService.updatePosition(posId, newPosName);
        return Result.okGetString("职位信息成功更新");
    }

    // 删除一条position
    @RequestMapping(value = "/deletePosition", method = RequestMethod.DELETE)
    public String deletePosition(@RequestParam("posId") int posId) {
        positionService.deletePosition(posId);
        return Result.okGetString("职位信息成功删除");
    }

    // 新建一条position
    @RequestMapping(value = "/addPosition", method = RequestMethod.POST)
    public String addPosition(@RequestParam("uid") int uid,
                              @RequestParam("posName") String posName) {
        int newPosId = positionService.addPosition(uid, posName);
        return Result.okGetStringByData("职位信息成功添加", newPosId);
    }
}
