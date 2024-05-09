package com.beetrb.redis_study.user.service;

import com.beetrb.redis_study.oauth2.domain.User;

public interface UserService {
    User getUser(String userId);
}
