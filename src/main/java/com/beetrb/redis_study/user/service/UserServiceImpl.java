package com.beetrb.redis_study.user.service;

import com.beetrb.redis_study.oauth2.domain.User;
import com.beetrb.redis_study.oauth2.exception.ApiException;
import com.beetrb.redis_study.oauth2.exception.ErrorCode;
import com.beetrb.redis_study.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RedisTemplate redisTemplate;
    private final UserRepository userRepository;

    public void save(final User user) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(user.getId(), user, Duration.ofSeconds(20));
    }
    @Override
    public User getUser(String userId) {
        Object result = redisTemplate.opsForValue().get(userId);

        if(result != null) {
            log.info(" ---------- 캐시에 저장된 User의 값 -----------");
            log.info("user    :  {}" , result.toString());
            return (User) result;
        }
        log.info(" ---------- DB에서 가져온 User의 값 -----------");

        User user = userRepository.findById(userId)
                                  .orElseThrow(()-> new ApiException(ErrorCode.NOT_FOUND_USER));
        log.info("user    :  {}", user.toString());
        this.save(user);

        return user;
    }
}
