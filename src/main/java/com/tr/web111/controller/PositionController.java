// PositionController.java
package com.tr.web111.controller;

import com.tr.web111.pojo.PositionPojo;
import com.tr.web111.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import utils.Result;

@Api(value = "岗位的相关接口")
@RestController
public class PositionController {

    @Autowired
    PositionService positionService;

    // 查找用户的所有position
    @ApiOperation(value="查找用户所有岗位", notes = "根据uid查找所有position")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true)
    )
    @RequestMapping(value = "/findPositions/{uid}", method = RequestMethod.GET)
    public String findAllPositionsByUid(@PathVariable("uid") int uid) {
        List<PositionPojo> positions = positionService.findAllPositionsByUid(uid);
        return Result.okGetStringByData("成功获取职位信息", positions);
    }

    // 查找名字（字符串匹配查找）
    @ApiOperation(value="字符串匹配查找岗位", notes = "根据uid和posName相似搜索岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "posName", value = "岗位名称", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/findPositionsByName", method = RequestMethod.GET)
    public String findPositionsByUidAndPosName(@RequestParam(value = "uid",required = true) int uid,
                                               @RequestParam(value = "posName",required = false) String posName) {
        List<PositionPojo> positions = positionService.findPositionsByUidAndPosName(uid, posName);
        return Result.okGetStringByData("成功获取职位信息", positions);
    }

    // 修改一条position
    @ApiOperation(value="修改一个岗位", notes = "根据posId和newPosName修改一个岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "posId", value = "岗位id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "newPosName", value = "岗位新名称", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/updatePosition", method = RequestMethod.POST)
    public String updatePosition(@RequestBody Map<String,String> position) {
        positionService.updatePosition(Integer.valueOf(position.get("posId")), (String) position.get("newPosName"));
        return Result.okGetString("职位信息成功更新");
    }

    // 删除一条position
    @ApiOperation(value="删除一个岗位", notes = "根据posId删除一个岗位")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "posId", value = "岗位id", dataType = "int", paramType = "query", required = true)
    )
    @RequestMapping(value = "/deletePosition", method = RequestMethod.DELETE)
    public String deletePosition(@RequestBody Map<String,Object> position) {
        try {
            positionService.deletePosition((Integer) position.get("posId"));
            return Result.okGetString("职位信息成功删除");
        } catch (DataIntegrityViolationException ex) {
            return Result.errorGetString("无法删除，因为存在相关联的标签。请先删除或修改这些标签后再试");
        } catch (Exception ex) {  // catch any other unexpected exceptions
            return Result.errorGetString("删除职位信息时发生错误: " + ex.getMessage());
        }
    }


    // 新建一条position
    @ApiOperation(value="新建一个岗位", notes = "根据uid和posName新建岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "posName", value = "岗位名称", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/addPosition", method = RequestMethod.POST)
    public String addPosition(@RequestBody Map<String,Object> position) {
        int newPosId = positionService.addPosition((Integer) position.get("uid"), (String) position.get("posName"));
        return Result.okGetStringByData("职位信息成功添加", newPosId);
    }
}
