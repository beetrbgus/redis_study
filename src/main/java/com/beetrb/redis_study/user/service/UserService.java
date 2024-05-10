package com.beetrb.redis_study.user.service;

import com.beetrb.redis_study.user.domain.User;

import java.util.List;

public interface UserService {
    User getUser(String userId);

    User createUser(User user);

    List<User> getAllUser();
}
