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
import java.util.Comparator;
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
    public List<TagPojo> findProblemsByTag(Integer type, Integer cateID, Integer level, Integer exp, Boolean finish, String editTimeStr, Integer posID, Integer did) {
        QueryWrapper<TagPojo> queryWrapper = new QueryWrapper<>();

        if (type != null) queryWrapper.eq("type", type);
        if (cateID != null) queryWrapper.eq("cateID", cateID);
        if (level != null) queryWrapper.eq("level", level);
        if (exp != 0) queryWrapper.eq("exp", exp);
        if (finish != null) queryWrapper.eq("finish", finish);
        if (editTimeStr != "未指定") {
            Date[] dateRange = parseEditTime(editTimeStr);
            if (dateRange != null) {
                queryWrapper.between("editTime", dateRange[0], dateRange[1]);
            }
        }
        if (posID != null) queryWrapper.eq("posID", posID);
        if (did != null) queryWrapper.eq("did", did);

        return tagDao.selectList(queryWrapper);
    }


    public TagPojo findProblemByTag(int pid, Integer type, Integer cateID, Integer level, Integer exp, Boolean finish, String editTimeStr, Integer posID, Integer did) {
        QueryWrapper<TagPojo> queryWrapper = new QueryWrapper<>();

        // 在这里增加pid的查询条件
        queryWrapper.eq("pid", pid);

        if (type != null) queryWrapper.eq("type", type);
        if (cateID != null) queryWrapper.eq("cateID", cateID);
        if (level != null) queryWrapper.eq("level", level);
        if (exp != 0) queryWrapper.eq("exp", exp);
        if (finish != null) queryWrapper.eq("finish", finish);
        if (editTimeStr != null) {
            Date[] dateRange = parseEditTime(editTimeStr);
            if (dateRange != null) {
                queryWrapper.between("editTime", dateRange[0], dateRange[1]);
            }
        }
        if (posID != null) queryWrapper.eq("posID", posID);
        if (did != null) queryWrapper.eq("did", did);

        return tagDao.selectOne(queryWrapper);
    }


    public List<ProblemTagStringDto> findProblemTagStringDtosByTag(Integer uid, String title, Integer type, Integer cateID, Integer level, Integer exp, Boolean finish, String editTimeStr, Integer posID, Integer did, String sort) {
        // 根据Tag查找问题
        List<TagPojo> tagPojoList = findProblemsByTag(type, cateID, level, exp, finish, editTimeStr, posID, did);

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

                // 使用新的服务方法获取名称，并进行null检查
                if(tagPojo.getCateID() != null) {
                    problemTagDto.setCateID(categoryService.findCateNameByCateId(tagPojo.getCateID()));
                } else {
                    problemTagDto.setCateID("");
                }

                problemTagDto.setLevel(tagPojo.getLevel());
                problemTagDto.setExp(tagPojo.getExp());
                problemTagDto.setFinish(tagPojo.getFinish());

                // 调用timeSinceLastEdit方法设置editTime
                problemTagDto.setEditTime(timeSinceLastEdit(tagPojo.getPid()));

                // 使用新的服务方法获取名称，并进行null检查
                if(tagPojo.getPosID() != null) {
                    problemTagDto.setPosID(positionService.findPosNameByPosId(tagPojo.getPosID()));
                } else {
                    problemTagDto.setPosID("");
                }

                // 使用新的服务方法获取名称，并进行null检查
                if(tagPojo.getDid() != null) {
                    problemTagDto.setDid(departmentService.findDepNameByDepId(tagPojo.getDid()));
                } else {
                    problemTagDto.setDid("");
                }

                // 使用新的服务方法获取名称，并进行null检查
                if(tagPojo.getCid() != null) {
                    problemTagDto.setCid(companyService.findCompNameByCompId(tagPojo.getCid()));
                } else {
                    problemTagDto.setCid("");
                }

                problemTagDtoList.add(problemTagDto);
            }
        }

        // 根据sort参数排序问题列表
        if (sort != null) {
            switch (sort) {
                case "难度":
                    problemTagDtoList.sort(Comparator.comparing(ProblemTagStringDto::getLevel, Comparator.nullsLast(Comparator.naturalOrder())));
                    break;
                case "添加时间":
                    problemTagDtoList.sort(Comparator.comparing(ProblemTagStringDto::getAddTime, Comparator.nullsLast(Comparator.naturalOrder())));
                    break;
                case "编辑时间":
                    problemTagDtoList.sort(Comparator.comparing(ProblemTagStringDto::getEditTime, Comparator.nullsLast(Comparator.naturalOrder())));
                    break;
                case "掌握程度":
                    problemTagDtoList.sort(Comparator.comparing(ProblemTagStringDto::getExp, Comparator.nullsLast(Comparator.naturalOrder())));
                    break;
                case "pid":
                    problemTagDtoList.sort(Comparator.comparing(ProblemTagStringDto::getPid, Comparator.nullsLast(Comparator.naturalOrder())));
                    break;
                default:
                    problemTagDtoList.sort(Comparator.comparing(ProblemTagStringDto::getPid, Comparator.nullsLast(Comparator.naturalOrder())));
                    break;
            }
        }

        return problemTagDtoList;
    }



    public ProblemTagStringDto findDtoByTagUid(Integer uid, Integer pid, Integer type, Integer cateID, Integer level, Integer exp, Boolean finish,String editTimeStr, Integer posID, Integer did) {
        // 根据pid和uid在ProblemService中查找Problem
        ProblemPojo problemPojo = problemService.findProblemByPidAndUid(pid, uid);

        // 如果找到符合条件的问题
        if (problemPojo != null) {
            // 查找标签
            TagPojo tagPojo = findProblemByTag(pid, type, cateID, level, exp, finish, editTimeStr, posID, did);

            // 如果找到符合条件的标签，创建ProblemTagStringDto并设置属性
            if (tagPojo != null) {
                ProblemTagStringDto problemTagDto = new ProblemTagStringDto();

                problemTagDto.setPid(problemPojo.getPid());
                problemTagDto.setUid(problemPojo.getUid());
                problemTagDto.setNote(problemPojo.getNote());
                problemTagDto.setCode(problemPojo.getCode());
                problemTagDto.setTitle(problemPojo.getTitle());
                problemTagDto.setDescription(problemPojo.getDescription());
                problemTagDto.setAddTime(problemPojo.getAddTime());
                problemTagDto.setType(tagPojo.getType());

                // 使用新的服务方法获取名称，并进行null检查
                if(tagPojo.getCateID() != null) {
                    problemTagDto.setCateID(categoryService.findCateNameByCateId(tagPojo.getCateID()));
                } else {
                    problemTagDto.setCateID("");
                }

                problemTagDto.setLevel(tagPojo.getLevel());
                problemTagDto.setExp(tagPojo.getExp());
                problemTagDto.setFinish(tagPojo.getFinish());

                // 调用timeSinceLastEdit方法设置editTime
                problemTagDto.setEditTime(timeSinceLastEdit(tagPojo.getPid()));

                // 使用新的服务方法获取名称，并进行null检查
                if(tagPojo.getPosID() != null) {
                    problemTagDto.setPosID(positionService.findPosNameByPosId(tagPojo.getPosID()));
                } else {
                    problemTagDto.setPosID("");
                }

                // 使用新的服务方法获取名称，并进行null检查
                if(tagPojo.getDid() != null) {
                    problemTagDto.setDid(departmentService.findDepNameByDepId(tagPojo.getDid()));
                } else {
                    problemTagDto.setDid("");
                }

                // 使用新的服务方法获取名称，并进行null检查
                if(tagPojo.getCid() != null) {
                    problemTagDto.setCid(companyService.findCompNameByCompId(tagPojo.getCid()));
                } else {
                    problemTagDto.setCid("");
                }

                return problemTagDto;
            }
        }

        return null;
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


    public Date[] parseEditTime(String editTimeStr) {
        LocalDate now = LocalDate.now();
        LocalDate startDate;
        Date[] dateRange = new Date[2];

        switch (editTimeStr) {
            case "近一周":
                startDate = now.minusWeeks(1);
                break;
            case "近一个月":
                startDate = now.minusMonths(1);
                break;
            case "近半年":
                startDate = now.minusMonths(6);
                break;
            case "超过半年":
                startDate = now.minusYears(50); // 随意设定一个较大的年份，可以根据实际情况调整
                break;
            default:
                return null; // 如果输入的字符串不匹配任何情况，返回null
        }

        dateRange[0] = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dateRange[1] = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return dateRange;
    }

    public void deleteTag(Integer pid) {
        tagDao.deleteById(pid);
    }
}
