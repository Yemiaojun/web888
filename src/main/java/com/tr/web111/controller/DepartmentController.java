// DepartmentController.java
package com.tr.web111.controller;

import com.tr.web111.pojo.DepartmentPojo;
import com.tr.web111.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import utils.Result;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    // 查找某公司所有部门
    @RequestMapping(value = "/findDepartments", method = RequestMethod.GET)
    public String findAllDepartmentsByCid(@RequestParam("cid") int cid) {
        List<DepartmentPojo> departments = departmentService.findAllDepartmentsByCid(cid);
        return Result.okGetStringByData("成功获取部门信息", departments);
    }

    // 查找名字（字符串匹配查找）
    @RequestMapping(value = "/findDepartmentsByName", method = RequestMethod.GET)
    public String findDepartmentsByCidAndDname(@RequestParam("cid") int cid,
                                               @RequestParam("dname") String dname) {
        List<DepartmentPojo> departments = departmentService.findDepartmentsByCidAndDname(cid, dname);
        return Result.okGetStringByData("成功获取部门信息", departments);
    }

    // 删除某公司下所有部门
    @RequestMapping(value = "/deleteDepartmentsByCid", method = RequestMethod.DELETE)
    public String deleteAllDepartmentsByCid(@RequestParam("cid") int cid) {
        departmentService.deleteAllDepartmentsByCid(cid);
        return Result.okGetString("公司下所有部门信息成功删除");
    }

    // 删除一条部门
    @RequestMapping(value = "/deleteDepartmentByDid", method = RequestMethod.DELETE)
    public String deleteDepartment(@RequestParam("did") int did) {
        departmentService.deleteDepartment(did);
        return Result.okGetString("部门信息成功删除");
    }

    // 新建一条部门
    @RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
    public String addDepartment(@RequestParam("uid") int uid,
                                @RequestParam("dname") String dname,
                                @RequestParam("cid") int cid) {
        int newDid = departmentService.addDepartment(uid, dname, cid);
        return Result.okGetStringByData("部门信息成功添加", newDid);
    }

    // 修改一条部门
    @RequestMapping(value = "/updateDepartment", method = RequestMethod.POST)
    public String updateDepartment(@RequestParam("did") int did,
                                   @RequestParam("newDname") String newDname) {
        departmentService.updateDepartment(did, newDname);
        return Result.okGetString("部门信息成功更新");
    }
}
