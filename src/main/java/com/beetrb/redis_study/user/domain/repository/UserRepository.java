package com.beetrb.redis_study.user.domain.repository;

import com.beetrb.redis_study.user.domain.User;
import com.beetrb.redis_study.user.domain.provider.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByDomesticIdAndSocialType(String domesticId, SocialType socialType);

    @Query(value = "select user from  User user order by Ra")
    Optional<User> findByRandomUser();
}
