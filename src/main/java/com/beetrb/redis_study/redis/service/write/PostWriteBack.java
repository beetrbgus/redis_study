package com.beetrb.redis_study.redis.service.write;

import com.beetrb.redis_study.post.domain.Post;
import com.beetrb.redis_study.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostWriteBack {
    private final RedisTemplate<String, Object> redisTemplate;
    private final PostRepository postRepository;

    private static final String POST_KEY = "postId::";

    /**
     * Redis Write Back
     */
    @Scheduled(fixedDelay = 1000L * 60 * 2) // 2분
    public void savePost() {
        try {
            log.info("\r\n 히히 돈다 돌아  : " + LocalDateTime.now());
            ScanOptions scanOptions = ScanOptions.scanOptions()
                                                .match(POST_KEY + "*")
                                                .build();
            // Cursor 방식은 get("key")와 다르게 기본 10개씩 key를 조회함
            Cursor postKeys = redisTemplate.scan(scanOptions);
            List<Post> posts = new ArrayList<>();
            while(postKeys.hasNext()) {
                String postKey = String.valueOf(postKeys.next());

                Post post = (Post) redisTemplate.opsForValue().get(postKey);
                if(post != null) {
                    posts.add(post);
                }
                // 키 제거
                redisTemplate.delete(postKey);
            }
            postRepository.saveAllAndFlush(posts);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("레디스 저장 과정 도중 에러가 발생했습니다.");
        }
    }
}
