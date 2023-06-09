// TagService.java
package com.tr.web111.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tr.web111.dao.TagDao;
import com.tr.web111.pojo.TagPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagService {

    @Autowired
    TagDao tagDao;

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

    // 更新 type
    public void updateType(int pid, int type) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setType(type);
            tagDao.updateById(tag);
        }
    }

    // 更新 cateID
    public void updateCateID(int pid, int cateID) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setCateID(cateID);
            tagDao.updateById(tag);
        }
    }

    // 更新 level
    public void updateLevel(int pid, int level) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setLevel(level);
            tagDao.updateById(tag);
        }
    }

    // 更新 exp
    public void updateExp(int pid, int exp) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setExp(exp);
            tagDao.updateById(tag);
        }
    }

    // 更新 finish
    public void updateFinish(int pid, boolean finish) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setFinish(finish);
            tagDao.updateById(tag);
        }
    }

    // 更新 editTime
    public void updateEditTime(int pid, Date editTime) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setEditTime(editTime);
            tagDao.updateById(tag);
        }
    }

    // 更新 posID
    public void updatePosID(int pid, int posID) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setPosID(posID);
            tagDao.updateById(tag);
        }
    }

    // 更新 did
    public void updateDid(int pid, int did) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setDid(did);
            tagDao.updateById(tag);
        }
    }

    public void updateCid(int pid, int cid) {
        TagPojo tag = tagDao.selectById(pid);
        if (tag != null) {
            tag.setCid(cid);
            tag.setDid(null);  // 将did设置为null
            tagDao.updateById(tag);
        }
    }
}
