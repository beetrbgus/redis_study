package com.beetrb.redis_study.oauth2.service.provider;

import com.beetrb.redis_study.user.domain.provider.ProviderTokenRes;
import com.beetrb.redis_study.user.domain.provider.ProviderUserInfo;

public interface SocialOAuth2CustomService {
    ProviderTokenRes obtainAccessToken(String authCode);

    ProviderUserInfo getUserInfo(String accessToken);
}
