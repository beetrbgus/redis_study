package com.beetrb.redis_study.user.domain.provider;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KakaoInfo {
    @Value("${bbeudde.social.kakao.client_id}")
    private String clientId;    // 애플리케이션의 클라이언트 ID
    @Value("${bbeudde.social.kakao.client_secret}")
    private String clientSecret;
    @Value("${bbeudde.social.kakao.admin}")
    private String adminSecret;
    @Value("${bbeudde.social.kakao.redirect}")
    private String redirectUri;
    @Value("${bbeudde.social.kakao.url.login}")
    private String loginUri;

    @Value("${bbeudde.social.kakao.url.logout}")
    private String logoutUri;
    @Value("${bbeudde.social.kakao.url.token}")
    private String tokenUrl;
    @Value("${bbeudde.social.kakao.url.profile}")
    private String profileUrl;

    public LinkedMultiValueMap<String, String> obtainTokenReqParam(String code) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");
        params.add("response_type", "code");
        params.add("code", code);

        return params;
    }
}
