package com.beetrb.redis_study.user.service;

import com.beetrb.redis_study.oauth2.exception.ApiException;
import com.beetrb.redis_study.oauth2.exception.ErrorCode;
import com.beetrb.redis_study.user.domain.User;
import com.beetrb.redis_study.user.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserServiceImpl implements UserService {
    private final RedisTemplate redisTemplate;
    private final UserRepository userRepository;

    public UserServiceImpl(RedisTemplate redisTemplate, UserRepository userRepository) {
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
    }

    public void redisSave(final User user) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("userId::" + user.getId(), user, Duration.ofSeconds(20));
    }
    @Override
    public User getUser(String userId) {
        /**
         * Look Aside Cache 패턴
         * Cache Store를 검색
         * cache hit 후 데이터가 있으면 반환.
         * 데이터가 없으면 DB에서 조회 - cache miss
         */
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
        this.redisSave(user);

        return user;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        User saveUser = userRepository.save(user);
        return saveUser;
    }

    @Override
    public List<User> getAllUser() {
        ScanOptions scanOptions = ScanOptions.scanOptions().match("*").build();
        Cursor scan = redisTemplate.scan(scanOptions);
        List<User> userList = new ArrayList<>();

        while (scan.hasNext()) {
            User user = (User)scan.next();
            userList.add(user);
        }

/*        Object result = redisTemplate.opsForValue().multiGet(userId);

        if(result != null) {
            log.info(" ---------- 캐시에 저장된 User의 값 -----------");
            log.info("user    :  {}" , result.toString());
            return (User) result;
        }
        log.info(" ---------- DB에서 가져온 User의 값 -----------");

        List<User> user = userRepository.findAll();
        user.stream().forEach(dbUser -> this.save(dbUser));
        log.info("user    :  {}", user.toString());

        return user;*/
        return userRepository.findAll();
    }
}
