// CompanyService.java
package com.tr.web111.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tr.web111.dao.CompanyDao;
import com.tr.web111.pojo.CompanyPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyDao companyDao;

    @Autowired
    DepartmentService departmentService;

    // 查找用户的所有公司
    public List<CompanyPojo> findAllCompaniesByUid(int uid) {
        return companyDao.selectList(new QueryWrapper<CompanyPojo>().eq("uid", uid));
    }

    // 根据名称（字符串匹配）查找公司
    public List<CompanyPojo> findCompaniesByUidAndCname(int uid, String cname) {
        if(cname != null){
        return companyDao.selectList(new QueryWrapper<CompanyPojo>()
                .eq("uid", uid)
                .like("cname", cname));}
        else{
            return companyDao.selectList(new QueryWrapper<CompanyPojo>()
                    .eq("uid", uid));
        }
    }

    // 修改一个公司
    public void updateCompany(int cid, String newCname) {
        CompanyPojo company = companyDao.selectById(cid);
        if (company != null) {
            company.setCname(newCname);
            companyDao.updateById(company);
        }
    }

    // 删除一个公司
    public void deleteCompany(int cid) {
        departmentService.deleteAllDepartmentsByCid(cid); // 先删除该公司的所有部门
        companyDao.deleteById(cid); // 再删除该公司
    }

    // 新建一个公司
    public int addCompany(int uid, String cname) {
        CompanyPojo newCompany = new CompanyPojo(cname, uid);
        companyDao.insert(newCompany);
        return newCompany.getCid();
    }
    public String findCompNameByCompId(int compID) {
        CompanyPojo company = companyDao.selectById(compID);
        return company != null ? company.getCname() : null;
    }
}
