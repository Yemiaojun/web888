// CompanyController.java
package com.tr.web111.controller;

import com.tr.web111.pojo.CompanyPojo;
import com.tr.web111.service.CompanyService;
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

@Api(value = "公司的相关接口")
@RestController
public class CompanyController {

    @Autowired
    CompanyService companyService;

    // 查找用户的所有公司
    @ApiOperation(value="查找所有公司", notes = "根据uid搜索公司")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true)
            )
    @RequestMapping(value = "/findCompanies/{uid}", method = RequestMethod.GET)
    public String findAllCompaniesByUid(@PathVariable("uid") int uid) {
        List<CompanyPojo> companies = companyService.findAllCompaniesByUid(uid);
        return Result.okGetStringByData("成功获取公司信息", companies);
    }

    // 根据名称（字符串匹配）查找公司
    @ApiOperation(value="字符串匹配查找公司", notes = "根据uid和cname相似搜索公司")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "cname", value = "公司名称", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/findCompaniesByName", method = RequestMethod.GET)
    public String findCompaniesByUidAndCname(@RequestParam(value = "uid", required = true) int uid,
                                             @RequestParam(value = "cname", required = false) String cname) {
        List<CompanyPojo> companies = companyService.findCompaniesByUidAndCname(uid, cname);
        return Result.okGetStringByData("成功获取公司信息", companies);
    }

    // 修改一个公司
    @ApiOperation(value="修改一个公司", notes = "根据cid和newCname修改公司")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "公司id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "newCname", value = "公司新名字", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/updateCompany", method = RequestMethod.POST)
    public String updateCompany(@RequestBody Map<String,String> company) {
        companyService.updateCompany(Integer.valueOf(company.get("cid")) , (String) company.get("newCname"));
        return Result.okGetString("公司信息成功更新");
    }

    // 删除一个公司
    @ApiOperation(value="删除一个公司", notes = "根据cid删除一个公司")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "cid", value = "公司id", dataType = "int", paramType = "query", required = true)
    )
    @RequestMapping(value = "/deleteCompany", method = RequestMethod.DELETE)
    public String deleteCompany(@RequestBody Map<String,Object> company) {
        try {
            companyService.deleteCompany((Integer) company.get("cid"));
            return Result.okGetString("公司信息成功删除");
        } catch (DataIntegrityViolationException ex) {
            // catch the exception when the foreign key constraint is violated
            return Result.errorGetString("无法删除，因为存在相关联的标签。请先删除或修改这些标签后再试");
        } catch (Exception ex) {
            // catch any other unexpected exceptions
            return Result.errorGetString("删除公司信息时发生错误: " + ex.getMessage());
        }
    }


    // 新建一个公司
    @ApiOperation(value="新建一个公司", notes = "根据uid和cname新建公司")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "cname", value = "公司名字", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/addCompany", method = RequestMethod.POST)
    public String addCompany(@RequestBody Map<String,String> company) {
        int newCid = companyService.addCompany(Integer.valueOf(company.get("uid")), (String) company.get("cname"));
        return Result.okGetStringByData("公司信息成功添加", newCid);
    }
}
