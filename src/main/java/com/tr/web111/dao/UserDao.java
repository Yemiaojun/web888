package com.tr.web111.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tr.web111.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<UserPojo> {

}
