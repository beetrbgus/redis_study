package com.beetrb.redis_study.oauth2.service;

import com.beetrb.redis_study.oauth2.domain.User;
import com.beetrb.redis_study.oauth2.domain.UserCustomPrincipal;
import com.beetrb.redis_study.oauth2.domain.provider.ProviderTokenRes;
import com.beetrb.redis_study.oauth2.domain.provider.ProviderUserInfo;
import com.beetrb.redis_study.oauth2.domain.provider.SocialType;
import com.beetrb.redis_study.oauth2.dto.AuthCodeReqDto;
import com.beetrb.redis_study.oauth2.jwt.AuthTokenConverter;
import com.beetrb.redis_study.oauth2.jwt.TokenInfo;
import com.beetrb.redis_study.oauth2.repository.UserRepository;
import com.beetrb.redis_study.oauth2.service.provider.SocialOAuth2CustomService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OAuth2CustomServiceImpl implements OAuth2CustomService {
    private final SocialOAuth2CustomService kakaoOAuth2Service;
    private final UserRepository userRepository;
    private final AuthTokenConverter authTokenConverter;
    public OAuth2CustomServiceImpl(@Qualifier(value = "kakaoSocialOAuth2Service") SocialOAuth2CustomService kakaoOAuth2Service,
                                   UserRepository userRepository, AuthTokenConverter authTokenConverter) {
        this.kakaoOAuth2Service = kakaoOAuth2Service;
        this.userRepository = userRepository;
        this.authTokenConverter = authTokenConverter;
    }

    @Override
    public TokenInfo login(AuthCodeReqDto authCodeReqDto, SocialType socialType) {
        ProviderUserInfo userInfo = getUserInfo(authCodeReqDto.getCode(), socialType);
        User user = saveOrUpdate(userInfo);
        UserCustomPrincipal userPrincipal = UserCustomPrincipal.create(user);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 토큰으로 변환 시키기
        TokenInfo tokenInfo = authTokenConverter.principalToTokenInfo(userPrincipal);
        tokenInfo.setUserId(user.getId());
        return tokenInfo;
    }

    private User saveOrUpdate(ProviderUserInfo providerUser) {
        User user = userRepository
            .findByDomesticIdAndSocialType(
                providerUser.getDomesticId(), providerUser.getSocialType()
            )
            .orElse(User.create(providerUser));
        user.setLastLoginedTime();
        return userRepository.save(user);
    }

    private ProviderUserInfo getUserInfo(String authCode, SocialType socialType) {
        if(SocialType.KAKAO == socialType) {
            ProviderTokenRes providerTokenRes = kakaoOAuth2Service.obtainAccessToken(authCode);
            return kakaoOAuth2Service.getUserInfo(providerTokenRes.getAccess_token());
        }
        return null;
    }
}
