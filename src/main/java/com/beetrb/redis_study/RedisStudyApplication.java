package com.beetrb.redis_study;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class RedisStudyApplication {
    private static final Log LOG = LogFactory.getLog(RedisStudyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RedisStudyApplication.class, args);
    }

}
