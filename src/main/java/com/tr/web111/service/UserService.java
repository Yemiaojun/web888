// UserService.java
package com.tr.web111.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tr.web111.dao.UserDao;
import com.tr.web111.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    // 添加用户，将用户名和密码作为参数。如果添加成功，则返回新用户的ID
    public int addUser(String username, String password) {
        UserPojo newUser = new UserPojo(username, password);
        userDao.insert(newUser);
        return newUser.getID();  // 获取新用户的ID
    }

    // 查找用户，将用户名和密码作为参数。如果查找成功，则返回对应的用户ID；否则，返回-1表示查找失败
    public int findUser(String username, String password) {
        UserPojo foundUser = userDao.selectOne(
                new QueryWrapper<UserPojo>()
                        .eq("username", username)
                        .eq("password", password)
        );
        // 如果找到对应的用户，则返回用户的ID；否则，返回-1表示查找失败
        return foundUser != null ? foundUser.getID() : -1;
    }
}
