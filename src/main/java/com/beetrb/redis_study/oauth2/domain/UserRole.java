package com.beetrb.redis_study.oauth2.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    MEMBER("member", "회원"),
    ADMIN("admin", "관리자"),
    ;

    private final String code;
    private final String value;

    public static UserRole matchKey(String code) {
        return Arrays.stream(UserRole.values())
            .filter(userRole -> userRole.name().equals(code))
            .findAny().get();
    }
}
