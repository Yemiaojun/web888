package com.tr.web111.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tr.web111.dao.UserDao;
import com.tr.web111.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceple {

    @Autowired
    UserDao userDao;

    public void addUser(String username,String password){
        userDao.insert(new UserPojo(username,password));
    }

    public Object findUserByName(String username){
        UserPojo pojo = userDao.selectOne(new QueryWrapper<UserPojo>().eq("username", username));
        System.out.println(pojo);
        return pojo;

    }
}
