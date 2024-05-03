package com.beetrb.redis_study.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class AppConfig {

    @Bean
    public HttpHeaders httpHeaders() {
        return new HttpHeaders();
    }
}
