// ProblemService.java
package com.tr.web111.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tr.web111.dao.ProblemDao;
import com.tr.web111.pojo.ProblemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {

    @Autowired
    ProblemDao problemDao;

    public void addProblem(int uid) {
        problemDao.insert(new ProblemPojo(uid, "no title","no description", "no note", "no code"));
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
