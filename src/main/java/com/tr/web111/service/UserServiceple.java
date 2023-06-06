package com.tr.web111.service;

import com.tr.web111.dao.UserDao;
import com.tr.web111.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public void addUser(String username,String password){
        userDao.insert(new UserPojo(username,password));
    }
}
