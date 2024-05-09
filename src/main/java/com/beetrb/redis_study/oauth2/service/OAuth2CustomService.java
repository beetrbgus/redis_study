package com.beetrb.redis_study.oauth2.service;

import com.beetrb.redis_study.oauth2.domain.provider.SocialType;
import com.beetrb.redis_study.oauth2.dto.AuthCodeReqDto;
import com.beetrb.redis_study.oauth2.jwt.TokenInfo;

public interface OAuth2CustomService {
    TokenInfo login(AuthCodeReqDto authCode, SocialType socialType);
}
