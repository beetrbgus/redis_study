package com.beetrb.redis_study.redis.config;

import io.lettuce.core.ReadFrom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    /**
     * <a href="https://docs.spring.io/spring-data/redis/reference/redis/connection-modes.html">Spring Data Redis</a>
     * Master / Replica 설정
     * 자동 장애 조치는 Sentiel을 사용하면 됨.
     * Lettuce는 데이터를 더 많은 Node에 안전하게 저장
     * 또, Master에 쓰기를 push하는 동안 Replica에서 데이터를 읽을 수 있음.
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // 사용할 읽기 / 쓰기 전략을 설정할 수 있음.
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration
                                                        .builder()
                                                        .readFrom(ReadFrom.REPLICA_PREFERRED)
                                                        .build();
        /**
         * AWS 또는 비공개 주소를 사용하는 경우 RedisStandaloneConfiguration 대신
         * RedisStaticMasterReplicaConfiguration을 사용
         * -> 개별 서버간 Pub/Sub 메시지 전파가 누락되어 Pub/Sub 지원 X
        */
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration("server", 6379);

        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        redisTemplate.setDefaultSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
