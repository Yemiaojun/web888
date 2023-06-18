// TagService.java
package com.tr.web111.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tr.web111.dao.TagDao;
import com.tr.web111.dto.ProblemTagStringDto;
import com.tr.web111.pojo.ProblemPojo;
import com.tr.web111.pojo.TagPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
@Service
public class TagService {

    @Autowired
    TagDao tagDao;

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProblemService problemService;

    // 根据标签查找问题
    public List<TagPojo> findProblemsByTag(Integer type, Integer cateID, Integer level, Integer exp, Boolean finish, Date editTime, Integer posID, Integer did) {
        QueryWrapper<TagPojo> queryWrapper = new QueryWrapper<>();

        if (type != null) queryWrapper.eq("type", type);
        if (cateID != null) queryWrapper.eq("cateID", cateID);
        if (level != null) queryWrapper.eq("level", level);
        if (exp != null) queryWrapper.eq("exp", exp);
        if (finish != null) queryWrapper.eq("finish", finish);
        if (editTime != null) queryWrapper.eq("editTime", editTime);
        if (posID != null) queryWrapper.eq("posID", posID);
        if (did != null) queryWrapper.eq("did", did);

        return tagDao.selectList(queryWrapper);
    }

    public List<ProblemTagStringDto> findProblemTagStringDtosByTag(Integer uid, String title, Integer type, Integer cateID, Integer level, Integer exp, Boolean finish, Date editTime, Integer posID, Integer did) {
        // 根据Tag查找问题
        List<TagPojo> tagPojoList = findProblemsByTag(type, cateID, level, exp, finish, editTime, posID, did);

        List<ProblemTagStringDto> problemTagDtoList = new ArrayList<>();

        for (TagPojo tagPojo : tagPojoList) {
            // 根据pid, uid和title在ProblemService中查找Problem
            ProblemPojo problemPojo = problemService.findProblemByPidAndUidAndTitle(tagPojo.getPid(), uid, title);

            // 如果找到符合条件的问题，添加到结果列表
            if (problemPojo != null) {
                // 创建一个新的ProblemTagStringDto并设置属性
                ProblemTagStringDto problemTagDto = new ProblemTagStringDto();

                problemTagDto.setPid(problemPojo.getPid());
                problemTagDto.setUid(problemPojo.getUid());
                problemTagDto.setNote(problemPojo.getNote());
                problemTagDto.setCode(problemPojo.getCode());
                problemTagDto.setTitle(problemPojo.getTitle());
                problemTagDto.setDescription(problemPojo.getDescription());
                problemTagDto.setAddTime(problemPojo.getAddTime());
                problemTagDto.setType(tagPojo.getType());

                // 使用新的服务方法获取名称
                problemTagDto.setCateID(categoryService.findCateNameByCateId(tagPojo.getCateID()));
                problemTagDto.setLevel(tagPojo.getLevel());
                problemTagDto.setExp(tagPojo.getExp());
                problemTagDto.setFinish(tagPojo.getFinish());

                // 调用timeSinceLastEdit方法设置editTime
                problemTagDto.setEditTime(timeSinceLastEdit(tagPojo.getPid()));

                problemTagDto.setPosID(positionService.findPosNameByPosId(tagPojo.getPosID()));
                problemTagDto.setDid(departmentService.findDepNameByDepId(tagPojo.getDid()));
                problemTagDto.setCid(companyService.findCompNameByCompId(tagPojo.getCid()));

                problemTagDtoList.add(problemTagDto);
            }
        }

        return problemTagDtoList;
    }



    public TagPojo findProblemByPid(int pid) {
        return tagDao.selectOne(new QueryWrapper<TagPojo>().eq("pid", pid));
    }
    // 更新 type
    public void updateType(int pid, int type) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setType(type);
            tag.setEditTime(new Date());
            tagDao.updateById(tag);

        }
    }

    // 更新 cateID
    public void updateCateID(int pid, int cateID) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setCateID(cateID);
            tag.setEditTime(new Date());
            tagDao.updateById(tag);
        }
    }

    // 更新 level
    public void updateLevel(int pid, int level) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setLevel(level);
            tag.setEditTime(new Date());
            tagDao.updateById(tag);
        }
    }

    // 更新 exp
    public void updateExp(int pid, int exp) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setExp(exp);
            tag.setEditTime(new Date());
            tagDao.updateById(tag);
        }
    }

    // 更新 finish
    public void updateFinish(int pid, boolean finish) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setFinish(finish);
            tag.setEditTime(new Date());
            tagDao.updateById(tag);
        }
    }

    // 更新 editTime
    public void updateEditTime(int pid, Date editTime) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setEditTime(editTime);
            tag.setEditTime(new Date());
            tagDao.updateById(tag);
        }
    }

    // 更新 posID
    public void updatePosID(int pid, int posID) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setPosID(posID);
            tag.setEditTime(new Date());
            tagDao.updateById(tag);
        }
    }

    // 更新 did
    public void updateDid(int pid, int did) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setDid(did);
            tag.setEditTime(new Date());
            tagDao.updateById(tag);
        }
    }

    public void updateCid(int pid, int cid) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setCid(cid);
            tag.setDid(null);  // 将did设置为null
            tag.setEditTime(new Date());
            tagDao.updateById(tag);
        }
    }

    public String timeSinceLastEdit(int pid) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            Date editDate = tag.getEditTime();
            LocalDate localEditDate = editDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate now = LocalDate.now();
            Period period = Period.between(localEditDate, now);
            int days = period.getDays();

            if (days < 7) {
                return "近一周";
            } else if (days < 30) {
                return "近一个月";
            } else if (days < 180) {
                return "近半年";
            } else {
                return "超过半年";
            }
        } else {
            return "无此pid对应的Tag信息";
        }
    }
}
