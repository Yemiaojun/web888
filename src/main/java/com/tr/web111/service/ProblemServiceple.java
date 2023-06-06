// ProblemServiceple.java
package com.tr.web111.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tr.web111.dao.ProblemDao;
import com.tr.web111.pojo.ProblemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemServiceple {

    @Autowired
    ProblemDao problemDao;

    public void addProblem(int uid, String note, String code) {
        problemDao.insert(new ProblemPojo(uid, note, code));
    }

    public List<ProblemPojo> findProblemsByUid(int uid) {
        return problemDao.selectList(new QueryWrapper<ProblemPojo>().eq("uid", uid));
    }
}
