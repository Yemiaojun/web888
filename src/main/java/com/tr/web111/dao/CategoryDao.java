// CategoryDao.java
package com.tr.web111.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tr.web111.pojo.CategoryPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryDao extends BaseMapper<CategoryPojo> {
}
