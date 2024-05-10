package com.beetrb.redis_study.post.domain.repository;

import com.beetrb.redis_study.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
