// CompanyController.java
package com.tr.web111.controller;

import com.tr.web111.pojo.CompanyPojo;
import com.tr.web111.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import utils.Result;

@RestController
public class CompanyController {

    @Autowired
    CompanyService companyService;

    // 查找用户的所有公司
    @RequestMapping(value = "/findCompanies", method = RequestMethod.GET)
    public String findAllCompaniesByUid(@RequestParam("uid") int uid) {
        List<CompanyPojo> companies = companyService.findAllCompaniesByUid(uid);
        return Result.okGetStringByData("成功获取公司信息", companies);
    }

    // 根据名称（字符串匹配）查找公司
    @RequestMapping(value = "/findCompaniesByName", method = RequestMethod.GET)
    public String findCompaniesByUidAndCname(@RequestParam("uid") int uid,
                                             @RequestParam("cname") String cname) {
        List<CompanyPojo> companies = companyService.findCompaniesByUidAndCname(uid, cname);
        return Result.okGetStringByData("成功获取公司信息", companies);
    }

    // 修改一个公司
    @RequestMapping(value = "/updateCompany", method = RequestMethod.POST)
    public String updateCompany(@RequestParam("cid") int cid,
                                @RequestParam("newCname") String newCname) {
        companyService.updateCompany(cid, newCname);
        return Result.okGetString("公司信息成功更新");
    }

    // 删除一个公司
    @RequestMapping(value = "/deleteCompany", method = RequestMethod.DELETE)
    public String deleteCompany(@RequestParam("cid") int cid) {
        companyService.deleteCompany(cid);
        return Result.okGetString("公司信息成功删除");
    }

    // 新建一个公司
    @RequestMapping(value = "/addCompany", method = RequestMethod.POST)
    public String addCompany(@RequestParam("uid") int uid,
                             @RequestParam("cname") String cname) {
        int newCid = companyService.addCompany(uid, cname);
        return Result.okGetStringByData("公司信息成功添加", newCid);
    }
}
