// DepartmentController.java
package com.tr.web111.controller;

import com.tr.web111.pojo.DepartmentPojo;
import com.tr.web111.service.DepartmentService;
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

@Api(value = "部门的相关接口")
@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    // 查找某公司所有部门
    @ApiOperation(value="查找公司所有部门", notes = "根据cid搜索所有公司下部门")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "cid", value = "公司id", dataType = "int", paramType = "query", required = true)
    )
    @RequestMapping(value = "/findDepartments/{cid}", method = RequestMethod.GET)
    public String findAllDepartmentsByCid(@PathVariable("cid") int cid) {
        List<DepartmentPojo> departments = departmentService.findAllDepartmentsByCid(cid);
        return Result.okGetStringByData("成功获取部门信息", departments);
    }

    // 查找名字（字符串匹配查找）
    @ApiOperation(value="字符串匹配查找部门", notes = "根据cid和dname相似搜索部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "公司id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "dname", value = "部门名称", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/findDepartmentsByName", method = RequestMethod.GET)
    public String findDepartmentsByCidAndDname(@RequestParam(value = "cid",required = true) int cid,
                                               @RequestParam(value = "dname",required = false) String dname) {
        List<DepartmentPojo> departments = departmentService.findDepartmentsByCidAndDname(cid, dname);
        return Result.okGetStringByData("成功获取部门信息", departments);
    }

    // 删除某公司下所有部门
    @ApiOperation(value="删除某公司下所有部门", notes = "根据cid删除公司下所有部门")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "cid", value = "公司id", dataType = "int", paramType = "query", required = true)
    )
    @RequestMapping(value = "/deleteDepartmentsByCid", method = RequestMethod.DELETE)
    public String deleteAllDepartmentsByCid(@RequestBody Map<String,Object> depart) {
        departmentService.deleteAllDepartmentsByCid((Integer) depart.get("cid"));
        return Result.okGetString("公司下所有部门信息成功删除");
    }

    // 删除一条部门
    @ApiOperation(value="删除一条部门", notes = "根据did删除一条部门")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "did", value = "部门id", dataType = "int", paramType = "query", required = true)
    )
    @RequestMapping(value = "/deleteDepartmentByDid", method = RequestMethod.DELETE)
    public String deleteDepartment(@RequestBody Map<String,Object> depart) {
        try {
            departmentService.deleteDepartment((Integer) depart.get("did"));
            return Result.okGetString("部门信息成功删除");
        } catch (DataIntegrityViolationException ex) {
            // catch the exception when the foreign key constraint is violated
            return Result.errorGetString("无法删除，因为存在相关联的标签。请先删除或修改这些标签后再试");
        } catch (Exception ex) {
            // catch any other unexpected exceptions
            return Result.errorGetString("删除部门信息时发生错误: " + ex.getMessage());
        }
    }


    // 新建一条部门
    @ApiOperation(value="新建一个部门", notes = "根据uid和dname和cid新建部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "dname", value = "部门名称", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "cid", value = "公司id", dataType = "int", paramType = "query", required = true)
    })
    @RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
    public String addDepartment(@RequestBody Map<String,Object> depart) {
        int newDid = departmentService.addDepartment((Integer) depart.get("uid"), (String) depart.get("dname"), (Integer) depart.get("cid"));
        return Result.okGetStringByData("部门信息成功添加", newDid);
    }

    // 修改一条部门
    @ApiOperation(value="修改一个部门", notes = "根据did和newDname修改部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "did", value = "部门id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "newDname", value = "部门新名称", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/updateDepartment", method = RequestMethod.POST)
    public String updateDepartment(@RequestBody Map<String,Object> depart) {
        departmentService.updateDepartment((Integer) depart.get("did"), (String) depart.get("newDname"));
        return Result.okGetString("部门信息成功更新");
    }
}
