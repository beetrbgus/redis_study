package com.beetrb.redis_study.post.controller;

import com.beetrb.redis_study.post.domain.Post;
import com.beetrb.redis_study.post.dto.request.CreatePostReqDTO;
import com.beetrb.redis_study.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/post")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping
    public ResponseEntity getFavoritePostList() {

        return ResponseEntity.of(null);
    }

    @PostMapping
    public void savePost(CreatePostReqDTO createPostReqDTO) {
        Post post = postService.savePostWriteThrough(createPostReqDTO);
    }
}
