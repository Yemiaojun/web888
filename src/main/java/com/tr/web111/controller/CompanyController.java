// CompanyController.java
package com.tr.web111.controller;

import com.tr.web111.pojo.CompanyPojo;
import com.tr.web111.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @RequestMapping(value = "/findCompanies", method = RequestMethod.GET)
    public String findAllCompaniesByUid(@RequestParam("uid") int uid) {
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
    public String findCompaniesByUidAndCname(@RequestParam("uid") int uid,
                                             @RequestParam("cname") String cname) {
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
    public String updateCompany(@RequestParam("cid") int cid,
                                @RequestParam("newCname") String newCname) {
        companyService.updateCompany(cid, newCname);
        return Result.okGetString("公司信息成功更新");
    }

    // 删除一个公司
    @ApiOperation(value="删除一个公司", notes = "根据cid删除一个公司")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "cid", value = "公司id", dataType = "int", paramType = "query", required = true)
    )
    @RequestMapping(value = "/deleteCompany", method = RequestMethod.DELETE)
    public String deleteCompany(@RequestParam("cid") int cid) {
        companyService.deleteCompany(cid);
        return Result.okGetString("公司信息成功删除");
    }

    // 新建一个公司
    @ApiOperation(value="新建一个公司", notes = "根据uid和cname新建公司")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "cname", value = "公司名字", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/addCompany", method = RequestMethod.POST)
    public String addCompany(@RequestParam("uid") int uid,
                             @RequestParam("cname") String cname) {
        int newCid = companyService.addCompany(uid, cname);
        return Result.okGetStringByData("公司信息成功添加", newCid);
    }
}
