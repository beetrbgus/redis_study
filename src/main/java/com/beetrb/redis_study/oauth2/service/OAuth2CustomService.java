package com.beetrb.redis_study.oauth2.service;

import com.beetrb.redis_study.oauth2.domain.provider.SocialType;
import com.beetrb.redis_study.oauth2.dto.AuthCodeReqDto;

public interface OAuth2CustomService {
    String login(AuthCodeReqDto authCode, SocialType socialType);
}
