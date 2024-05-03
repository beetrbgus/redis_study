package com.beetrb.redis_study.oauth2.domain.provider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
    NAVER("naver", "네이버"),
    KAKAO("kakao", "카카오"),
    GOOGLE("google", "구글"),
    ;
    private final String code;
    private final String value;

}
