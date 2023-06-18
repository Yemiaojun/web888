// ProblemService.java
package com.tr.web111.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tr.web111.dao.ProblemDao;
import com.tr.web111.dao.TagDao;
import com.tr.web111.dto.ProblemTagDto;
import com.tr.web111.dto.ProblemTagStringDto;
import com.tr.web111.pojo.ProblemPojo;
import com.tr.web111.pojo.TagPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class ProblemService {

    @Autowired
    ProblemDao problemDao;

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
    private TagService tagService;

    public void addProblem(Integer uid, String title, String description, Integer type, Integer level, Integer cateID, Integer did, Integer posID) {
        // 插入问题
        ProblemPojo problem = new ProblemPojo(title, description, "无备注", "无代码",uid);
        problemDao.insert(problem);

        // 获取插入问题后自动生成的ID
        int pid = problem.getPid();

        // 创建新的TagPojo对象，
        TagPojo tag = new TagPojo();
        tag.setPid(pid);
        tag.setEditTime(new Date());
        if(type != -1) tag.setType(type);
        if(level != -1) tag.setLevel(level);
        if(cateID != -1) tag.setCateID(cateID);
        if(level != -1) tag.setLevel(level);
        if(did != -1) tag.setDid(did);
        if(posID != -1) tag.setPosID(posID);
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
    public List<ProblemTagStringDto> findProblemTagStringDtosByUid(int uid) {
        List<ProblemTagStringDto> problemTagDtoList = new ArrayList<>();

        List<ProblemPojo> problemPojoList = problemDao.selectList(new QueryWrapper<ProblemPojo>().eq("uid", uid));

        for (ProblemPojo problemPojo : problemPojoList) {
            TagPojo tagPojo = tagDao.selectOne(new QueryWrapper<TagPojo>().eq("pid", problemPojo.getPid()));

            ProblemTagStringDto problemTagDto = new ProblemTagStringDto();

            problemTagDto.setPid(problemPojo.getPid());
            problemTagDto.setUid(problemPojo.getUid());
            problemTagDto.setNote(problemPojo.getNote());
            problemTagDto.setCode(problemPojo.getCode());
            problemTagDto.setTitle(problemPojo.getTitle());
            problemTagDto.setDescription(problemPojo.getDescription());
            problemTagDto.setAddTime(problemPojo.getAddTime());
            problemTagDto.setType(tagPojo.getType());

            // 检查是否为null
            if(tagPojo.getCateID() != null) {
                problemTagDto.setCateID(categoryService.findCateNameByCateId(tagPojo.getCateID()));
            } else {
                problemTagDto.setCateID(""); // 如果为null则设置为空字符串
            }

            problemTagDto.setLevel(tagPojo.getLevel());
            problemTagDto.setExp(tagPojo.getExp());
            problemTagDto.setFinish(tagPojo.getFinish());

            // 调用timeSinceLastEdit方法设置editTime
            problemTagDto.setEditTime(tagService.timeSinceLastEdit(tagPojo.getPid()));

            // 检查是否为null
            if(tagPojo.getPosID() != null) {
                problemTagDto.setPosID(positionService.findPosNameByPosId(tagPojo.getPosID()));
            } else {
                problemTagDto.setPosID(""); // 如果为null则设置为空字符串
            }

            // 检查是否为null
            if(tagPojo.getDid() != null) {
                problemTagDto.setDid(departmentService.findDepNameByDepId(tagPojo.getDid()));
            } else {
                problemTagDto.setDid(""); // 如果为null则设置为空字符串
            }

            // 检查是否为null
            if(tagPojo.getCid() != null) {
                problemTagDto.setCid(companyService.findCompNameByCompId(tagPojo.getCid()));
            } else {
                problemTagDto.setCid(""); // 如果为null则设置为空字符串
            }

            problemTagDtoList.add(problemTagDto);
        }

        return problemTagDtoList;
    }



    public List<ProblemTagDto> findProblemTagDtosByUid(int uid) {
        List<ProblemTagDto> problemTagDtoList = new ArrayList<>();

        List<ProblemPojo> problemPojoList = problemDao.selectList(new QueryWrapper<ProblemPojo>().eq("uid", uid));

        for (ProblemPojo problemPojo : problemPojoList) {
            TagPojo tagPojo = tagDao.selectOne(new QueryWrapper<TagPojo>().eq("pid", problemPojo.getPid()));


            ProblemTagDto problemTagDto = new ProblemTagDto();

            problemTagDto.setPid(problemPojo.getPid());
            problemTagDto.setUid(problemPojo.getUid());
            problemTagDto.setNote(problemPojo.getNote());
            problemTagDto.setCode(problemPojo.getCode());
            problemTagDto.setTitle(problemPojo.getTitle());
            problemTagDto.setDescription(problemPojo.getDescription());
            problemTagDto.setAddTime(problemPojo.getAddTime());
            problemTagDto.setType(tagPojo.getType());
            problemTagDto.setCateID(tagPojo.getCateID());
            problemTagDto.setLevel(tagPojo.getLevel());
            problemTagDto.setExp(tagPojo.getExp());
            problemTagDto.setFinish(tagPojo.getFinish());
            problemTagDto.setEditTime(tagPojo.getEditTime());
            problemTagDto.setPosID(tagPojo.getPosID());
            problemTagDto.setDid(tagPojo.getDid());
            problemTagDto.setCid(tagPojo.getCid());

            problemTagDtoList.add(problemTagDto);
        }

        return problemTagDtoList;
    }

    public List<ProblemTagStringDto> findProblemsByUidAndTitle(int uid, String title) {
        List<ProblemTagStringDto> problemTagDtoList = new ArrayList<>();
        List<ProblemPojo> problemPojoList = problemDao.selectList(new QueryWrapper<ProblemPojo>()
                .eq("uid", uid)
                .like("title", title));
        for (ProblemPojo problemPojo : problemPojoList) {
            TagPojo tagPojo = tagDao.selectOne(new QueryWrapper<TagPojo>().eq("pid", problemPojo.getPid()));

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
            problemTagDto.setEditTime(tagService.timeSinceLastEdit(tagPojo.getPid()));

            problemTagDto.setPosID(positionService.findPosNameByPosId(tagPojo.getPosID()));
            problemTagDto.setDid(departmentService.findDepNameByDepId(tagPojo.getDid()));
            problemTagDto.setCid(companyService.findCompNameByCompId(tagPojo.getCid()));

            problemTagDtoList.add(problemTagDto);
        }

        return problemTagDtoList;
    }

    public List<ProblemTagStringDto> findProblemTagStringDtosByUidSortByLevel(int uid) {
        List<ProblemTagStringDto> problemTagDtoList = findProblemTagStringDtosByUid(uid);

        // 使用 Stream API 进行排序
        problemTagDtoList.sort(Comparator.comparing(ProblemTagStringDto::getLevel));

        return problemTagDtoList;
    }

    public List<ProblemTagStringDto> findProblemTagStringDtosByUidSortByAddTime(int uid) {
        List<ProblemTagStringDto> problemTagDtoList = findProblemTagStringDtosByUid(uid);
        problemTagDtoList.sort(Comparator.comparing(ProblemTagStringDto::getAddTime));
        return problemTagDtoList;
    }

    public List<ProblemTagStringDto> findProblemTagStringDtosByUidSortByExp(int uid) {
        List<ProblemTagStringDto> problemTagDtoList = findProblemTagStringDtosByUid(uid);
        problemTagDtoList.sort(Comparator.comparing(ProblemTagStringDto::getExp));
        return problemTagDtoList;
    }

}
