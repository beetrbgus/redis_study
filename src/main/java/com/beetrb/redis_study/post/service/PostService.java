package com.beetrb.redis_study.post.service;

import com.beetrb.redis_study.post.domain.Post;
import com.beetrb.redis_study.post.dto.request.CreatePostReqDTO;

public interface PostService {
    Post savePostWriteThrough(CreatePostReqDTO createPostReqDTO);

    Post savePost(CreatePostReqDTO createPostReqDTO);

    Post getPost(Long postId);
}
