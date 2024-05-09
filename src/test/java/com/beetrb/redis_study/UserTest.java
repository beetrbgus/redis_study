package com.beetrb.redis_study;

import com.beetrb.redis_study.oauth2.domain.User;
import com.beetrb.redis_study.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
    @Autowired
    UserService userService;

    @Test
    public void getUser() {
        String userId = "0FZH1ZKP1R6NA";
        User user = userService.getUser(userId);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(userId, user.getId());
    }

}
