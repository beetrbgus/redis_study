package com.beetrb.redis_study.post.service;

import com.beetrb.redis_study.oauth2.exception.ApiException;
import com.beetrb.redis_study.oauth2.exception.ErrorCode;
import com.beetrb.redis_study.post.domain.Post;
import com.beetrb.redis_study.post.domain.repository.PostRepository;
import com.beetrb.redis_study.post.dto.request.CreatePostReqDTO;
import com.beetrb.redis_study.redis.service.PostRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostRedisRepository postRedisRepository;

    /**
     * Write-Through
     *
     * @param createPostReqDTO
     * @return
     */
    @Override
    @Transactional
    public Post savePostWriteThrough(CreatePostReqDTO createPostReqDTO) {
        Post post = Post.create(createPostReqDTO);
        postRepository.save(post);
        postRedisRepository.savePost(post);
        return post;
    }

    @Override
    @Transactional
    public Post savePost(CreatePostReqDTO createPostReqDTO) {
        Post post = Post.create(createPostReqDTO);
        postRepository.save(post);
        return post;
    }

    @Override
    public Post getPost(Long postId) {
        Optional<Post> postOptional = postRedisRepository.getPost(postId);

        if(postOptional.isPresent()) {
            Post post = postOptional.get();
            log.info("레디스에서 가져온 데이터 : Post의 제목은  {}", post.getTitle());
            return post;
        } else {
            Post post = postRepository.findById(postId)
                                       .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));
            postRedisRepository.savePost(post);
            log.info("DB에서 가져온 데이터 : Post의 제목은  {}", post.getTitle());
            return post;
        }
    }

}