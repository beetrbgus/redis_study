package com.beetrb.redis_study.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;

/**
 * 고 가용성으로 Redis를 처리하기 위해
 * Spring Data Redis는
 * RedisSentielConfiguration을 사용해 Redis Sentinel을 지원.
 */
@Configuration
public class RedisSentinelApplicationConfig {

    /**
     * Lettuce
     */
    /*@Bean
    public RedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(
            sentinelConfiguration(),
            LettuceClientConfiguration.defaultConfiguration()
        );
    }
    */
    @Bean
    public RedisSentinelConfiguration sentinelConfiguration() {
        return new RedisSentinelConfiguration()
                .master("beetrbmaster")
                .sentinel("localhost", 26379)
                .sentinel("localhost", 26380)
                .sentinel("localhost", 26381);
    }
}
