// PositionDao.java
package com.tr.web111.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tr.web111.pojo.PositionPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PositionDao extends BaseMapper<PositionPojo> {
}
