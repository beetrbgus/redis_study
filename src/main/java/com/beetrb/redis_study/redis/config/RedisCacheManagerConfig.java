package com.beetrb.redis_study.redis.config;

import com.beetrb.redis_study.post.domain.Post;
import com.beetrb.redis_study.user.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheManagerConfig {
    @Value("${key.post}")
    private String POST_ID;
    @Value("${key.user}")
    private String USER_ID;
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration postConfig =
            RedisCacheConfiguration.defaultCacheConfig()
                .prefixCacheNameWith(POST_ID + "::")
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(Post.class)))
                .entryTtl(Duration.ofMinutes(3L))
            ;
        RedisCacheConfiguration userConfig = RedisCacheConfiguration.defaultCacheConfig()
            .prefixCacheNameWith(USER_ID + "::")
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(User.class)))
            .entryTtl(Duration.ofMinutes(3L));
        return RedisCacheManager.RedisCacheManagerBuilder
                    .fromConnectionFactory(connectionFactory)
                    .withCacheConfiguration("Post", postConfig)
                    .withCacheConfiguration("User", userConfig)
                    .build();
    }

    /**
     * Jedis
     */
/*    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory(
            new RedisStandaloneConfiguration("server",6379)
        );
    }*/
}
