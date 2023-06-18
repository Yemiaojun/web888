// DepartmentService.java
package com.tr.web111.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tr.web111.dao.DepartmentDao;
import com.tr.web111.pojo.DepartmentPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentDao departmentDao;

    // 查找某公司所有部门
    public List<DepartmentPojo> findAllDepartmentsByCid(int cid) {
        return departmentDao.selectList(new QueryWrapper<DepartmentPojo>().eq("cid", cid));
    }

    // 查找名字（字符串匹配查找）
    public List<DepartmentPojo> findDepartmentsByCidAndDname(int cid, String dname) {
        return departmentDao.selectList(new QueryWrapper<DepartmentPojo>()
                .eq("cid", cid)
                .like("dname", dname)); // 使用like进行模糊匹配
    }

    // 删除某公司下所有部门
    public void deleteAllDepartmentsByCid(int cid) {
        departmentDao.delete(new QueryWrapper<DepartmentPojo>().eq("cid", cid));
    }

    // 删除一条部门
    public void deleteDepartment(int did) {
        departmentDao.deleteById(did);
    }

    // 新建一条部门
    public int addDepartment(int uid, String dname, int cid) {
        DepartmentPojo newDepartment = new DepartmentPojo(cid, dname, uid);
        departmentDao.insert(newDepartment);
        return newDepartment.getDid();  // 返回新部门的ID
    }

    // 修改一条部门
    public void updateDepartment(int did, String newDname) {
        DepartmentPojo department = departmentDao.selectById(did);
        if (department != null) {
            department.setDname(newDname);
            departmentDao.updateById(department);
        }
    }

    public String findDepNameByDepId(int depID) {
        DepartmentPojo department = departmentDao.selectById(depID);
        return department != null ? department.getDname() : null;
    }
}
