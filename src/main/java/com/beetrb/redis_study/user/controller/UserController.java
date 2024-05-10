package com.beetrb.redis_study.user.controller;

import com.beetrb.redis_study.user.domain.User;
import com.beetrb.redis_study.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable("userId") String userId) {
        long startTime = System.currentTimeMillis();
        User user = userService.getUser(userId);
        long endTime = System.currentTimeMillis();
        log.info("총 걸린 시간 :   {}ms", endTime - startTime );
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.getAllUser();

        return ResponseEntity.ok(allUser);
    }
}
