package com.beetrb.redis_study.oauth2.service;

import com.beetrb.redis_study.oauth2.domain.provider.ProviderTokenRes;
import com.beetrb.redis_study.oauth2.domain.provider.ProviderUserInfo;
import com.beetrb.redis_study.oauth2.domain.provider.SocialType;
import com.beetrb.redis_study.oauth2.jwt.TokenInfo;
import com.beetrb.redis_study.oauth2.service.provider.SocialOAuth2CustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OAuth2CustomServiceImpl implements OAuth2CustomService {
    private final SocialOAuth2CustomService kakaoOAuth2Service;

    public OAuth2CustomServiceImpl(@Qualifier(value = "kakaoSocialOAuth2Service") SocialOAuth2CustomService kakaoOAuth2Service) {
        this.kakaoOAuth2Service = kakaoOAuth2Service;
    }

    @Override
    public TokenInfo login(String authCode, SocialType socialType) {
        ProviderUserInfo userInfo = getUserInfo(authCode, socialType);
        return null;
    }

    private ProviderUserInfo getUserInfo(String authCode, SocialType socialType) {
        if(SocialType.KAKAO == socialType) {
            ProviderTokenRes providerTokenRes = kakaoOAuth2Service.obtainAccessToken(authCode);
            return kakaoOAuth2Service.getUserInfo(providerTokenRes.getAccess_token());
        }
        return null;
    }
}
