// ProblemService.java
package com.tr.web111.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tr.web111.dao.ProblemDao;
import com.tr.web111.dao.TagDao;
import com.tr.web111.pojo.ProblemPojo;
import com.tr.web111.pojo.TagPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProblemService {

    @Autowired
    ProblemDao problemDao;
    TagDao tagDao;


    public void addProblem(int uid) {
        // 插入问题
        ProblemPojo problem = new ProblemPojo(uid, "未命名","无描述", "无备注", "无代码");
        problemDao.insert(problem);

        // 获取插入问题后自动生成的ID
        int pid = problem.getPid();

        // 创建新的TagPojo对象，除了pid和editTime外，其余都为null
        TagPojo tag = new TagPojo();
        tag.setPid(pid);
        tag.setEditTime(new Date());
        // 插入tag到数据库
        tagDao.insert(tag);
    }

    public List<ProblemPojo> findProblemsByUid(int uid) {
        return problemDao.selectList(new QueryWrapper<ProblemPojo>().eq("uid", uid));
    }

    public ProblemPojo findProblemByPid(int pid) {
        return problemDao.selectOne(new QueryWrapper<ProblemPojo>().eq("pid", pid));
    }

    public List<ProblemPojo> findProblemsByPidList(List<Integer> pidList) {
        return problemDao.selectList(new QueryWrapper<ProblemPojo>().in("pid", pidList));
    }

    public void updateNote(int pid, String note) {
        ProblemPojo problem = findProblemByPid(pid);
        if (problem != null) {
            problem.setNote(note);
            problemDao.updateById(problem);
        }
    }

    public void updateCode(int pid, String code) {
        ProblemPojo problem = findProblemByPid(pid);
        if (problem != null) {
            problem.setCode(code);
            problemDao.updateById(problem);
        }
    }

    public void updateDescription(int pid, String description) {
        ProblemPojo problem = findProblemByPid(pid);
        if (problem != null) {
            problem.setDescription(description);
            problemDao.updateById(problem);
        }
    }

    public void updateTitle(int pid, String title) {
        ProblemPojo problem = findProblemByPid(pid);
        if (problem != null) {
            problem.setTitle(title);
            problemDao.updateById(problem);
        }
    }
}
