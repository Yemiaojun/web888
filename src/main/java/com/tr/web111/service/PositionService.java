// PositionService.java
package com.tr.web111.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tr.web111.dao.PositionDao;
import com.tr.web111.pojo.PositionPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    @Autowired
    PositionDao positionDao;

    // 查找用户的所有position
    public List<PositionPojo> findAllPositionsByUid(int uid) {
        return positionDao.selectList(new QueryWrapper<PositionPojo>().eq("uid", uid));
    }

    // 查找名字（字符串匹配查找）
    public List<PositionPojo> findPositionsByUidAndPosName(int uid, String posName) {
        return positionDao.selectList(new QueryWrapper<PositionPojo>()
                .eq("uid", uid)
                .like("posName", posName)); // 使用like进行模糊匹配
    }

    // 修改一条position
    public void updatePosition(int posId, String newPosName) {
        PositionPojo position = positionDao.selectById(posId);
        if (position != null) {
            position.setPosName(newPosName);
            positionDao.updateById(position);
        }
    }

    // 删除一条position
    public void deletePosition(int posId) {
        positionDao.deleteById(posId);
    }

    // 新建一条position
    public int addPosition(int uid, String posName) {
        PositionPojo newPosition = new PositionPojo(posName, uid);
        positionDao.insert(newPosition);
        return newPosition.getPosID();  // 返回新岗位的ID
    }

    public String findPosNameByPosId(int posID) {
        PositionPojo position = positionDao.selectById(posID);
        return position != null ? position.getPosName() : null;
    }
}
