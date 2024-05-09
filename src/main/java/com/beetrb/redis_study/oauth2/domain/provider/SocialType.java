package com.beetrb.redis_study.oauth2.domain.provider;

import com.beetrb.redis_study.oauth2.exception.ApiException;
import com.beetrb.redis_study.oauth2.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SocialType {
    NAVER("naver", "네이버"),
    KAKAO("kakao", "카카오"),
    GOOGLE("google", "구글"),
    ;
    private final String code;
    private final String value;

    public static SocialType getSocial(String socialType) {
        return Arrays.stream(SocialType.values())
            .filter(social -> social.code.equals(socialType) || social.name().equals(socialType))
            .findFirst()
            .orElseThrow(() -> new ApiException("올바른 소셜타입이 아닙니다.", ErrorCode.SYSTEM_EXCEPTION));
    }
}
