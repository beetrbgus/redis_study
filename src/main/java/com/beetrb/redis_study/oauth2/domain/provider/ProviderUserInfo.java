package com.beetrb.redis_study.oauth2.domain.provider;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProviderUserInfo {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String userId;
    private String name;
    private String email;
    private String domesticId; // provider 내 에서 유니크한 아이디
    private SocialType socialType;
    private String gender;
    private String ageRange;

    public static ProviderUserInfo of(
        String userId, SocialType providerId, Map<String, Object> attributes) {
        if(SocialType.KAKAO == providerId) {
            return ofKakao("id", attributes);
        }
        return of(userId, providerId, attributes);
    }

    private static ProviderUserInfo ofKakao(String userNameAttributeName
        , Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return ProviderUserInfo.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .userId(String.valueOf(kakaoAccount.get(userNameAttributeName)))
                .name((String) profile.getOrDefault("nickname", ""))
                .email((String) kakaoAccount.getOrDefault("email", ""))
                .domesticId(String.valueOf(attributes.get(userNameAttributeName)))
                .socialType(SocialType.KAKAO)
                .gender((String)kakaoAccount.getOrDefault("gender",""))
                .ageRange((String) kakaoAccount.getOrDefault("age_range", ""))
                .build();
    }

}
