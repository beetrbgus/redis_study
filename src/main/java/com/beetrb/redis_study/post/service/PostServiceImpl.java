package com.beetrb.redis_study.post.service;

import com.beetrb.redis_study.oauth2.exception.ApiException;
import com.beetrb.redis_study.oauth2.exception.ErrorCode;
import com.beetrb.redis_study.post.domain.Post;
import com.beetrb.redis_study.post.domain.repository.PostRepository;
import com.beetrb.redis_study.post.dto.request.CreatePostReqDTO;
import com.beetrb.redis_study.redis.service.PostRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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

    public Post getPost(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));
        post.increaseViewCount();
        return post;
    }

}