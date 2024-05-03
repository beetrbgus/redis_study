package com.beetrb.redis_study.oauth2.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    NORMAL("normal", "일반")
    , BLOCK("block", "차단")
    , DELETED("deleted", "삭제")
    ;
    private final String code;
    private final String value;
}
