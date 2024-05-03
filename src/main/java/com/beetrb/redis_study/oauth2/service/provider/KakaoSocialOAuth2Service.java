package com.beetrb.redis_study.oauth2.service.provider;

import com.beetrb.redis_study.oauth2.domain.provider.KakaoInfo;
import com.beetrb.redis_study.oauth2.domain.provider.ProviderTokenRes;
import com.beetrb.redis_study.oauth2.domain.provider.ProviderUserInfo;
import com.beetrb.redis_study.oauth2.domain.provider.SocialType;
import com.beetrb.redis_study.oauth2.exception.ApiException;
import com.beetrb.redis_study.oauth2.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoSocialOAuth2Service implements SocialOAuth2CustomService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final HttpHeaders headers;
    private final KakaoInfo kakaoInfo;

    @Override
    public ProviderTokenRes obtainAccessToken(String authCode) {
        try {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            LinkedMultiValueMap<String, String> params = kakaoInfo.obtainTokenReqParam(authCode);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(kakaoInfo.getTokenUrl(), request, String.class);

            ProviderTokenRes providerTokenRes = objectMapper.readValue(response.getBody(), ProviderTokenRes.class);
            return providerTokenRes;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(e.getMessage(), ErrorCode.LOGIN_FAIL);
        } finally {
            headers.clear();
        }
    }
    @Override
    public ProviderUserInfo getUserInfo(String accessToken) {
        try {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.postForEntity(kakaoInfo.getProfileUrl(), request, String.class);

            if(response.getStatusCode() == HttpStatus.OK) {
                Map<String,Object> kakaoOAuthResponseDto = objectMapper.readValue(response.getBody(), Map.class);
                Map<String,Object> kakao_account = (Map<String,Object>)kakaoOAuthResponseDto.get("kakao_account");
                return ProviderUserInfo.of((String)kakao_account.get("id"), SocialType.KAKAO, kakaoOAuthResponseDto);
            } else {
                throw new ApiException(ErrorCode.LOGIN_FAIL);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ApiException(ErrorCode.SYSTEM_EXCEPTION);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ErrorCode.SYSTEM_EXCEPTION);
        } finally {
            headers.clear();
        }
    }
}
