package com.tr.web111;

import com.tr.web111.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Web111ApplicationTests {

    @Autowired
    UserService userService;
    @Test
    void contextLoads() {
        userService.addUser( "2", "1");
    }

}
