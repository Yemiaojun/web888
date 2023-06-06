// ProblemDao.java
package com.tr.web111.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tr.web111.pojo.ProblemPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProblemDao extends BaseMapper<ProblemPojo> {

}
