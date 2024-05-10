package com.beetrb.redis_study.redis.service.write;

import com.beetrb.redis_study.post.domain.Post;
import com.beetrb.redis_study.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostWriteBack {
    private final RedisTemplate<String, Object> redisTemplate;
    private final PostRepository postRepository;

    private static final String POST_KEY = "postId::";

    @Scheduled(fixedDelay = 1000L * 60 * 2) //
    public void savePost() {
        log.info("\r\n 히히 돈다 돌아  : " + LocalDateTime.now());
        ScanOptions scanOptions = ScanOptions.scanOptions()
                                            .match(POST_KEY + "*")
                                            .build();
        Cursor postKeys = redisTemplate.scan(scanOptions);

        while(postKeys.hasNext()) {
            String postKey = String.valueOf(postKeys.next());
            String postId = postKey.substring(POST_KEY.length());

            Post post = (Post) redisTemplate.opsForValue().get(postKey);
            if(post != null) {
                postRepository.save(post);
            }
            // 키 제거
            redisTemplate.delete(postKey);
        }
    }
}
