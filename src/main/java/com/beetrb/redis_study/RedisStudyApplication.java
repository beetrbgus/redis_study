package com.beetrb.redis_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;

@EnableJpaAuditing
@SpringBootApplication
public class RedisStudyApplication {
    public static void main(String[] args) throws IOException {

        SpringApplication.run(RedisStudyApplication.class, args);
    }

}
