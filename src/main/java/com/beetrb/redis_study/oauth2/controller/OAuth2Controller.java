package com.beetrb.redis_study.oauth2.controller;

import com.beetrb.redis_study.oauth2.domain.provider.SocialType;
import com.beetrb.redis_study.oauth2.dto.AuthCodeReqDto;
import com.beetrb.redis_study.oauth2.jwt.TokenInfo;
import com.beetrb.redis_study.oauth2.jwt.UserToken;
import com.beetrb.redis_study.oauth2.service.OAuth2CustomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class OAuth2Controller {
    private final OAuth2CustomService oAuthUserService;
    @Value("${bbeudde.auth.header.scheme}")
    private String TOKEN_SCHEME;

    @PostMapping("oauth2/auth/{platformType}")
    public void login(@PathVariable String platformType){
        log.info(platformType);
        log.info(platformType);
    }
    /**
     * TODO - prod 환경에서 제거
     * 인가코드를 받아서 X-BBEUDDE-TOKEN으로 반환한다.
     * @param authCodeReqDto
     */
    @GetMapping("oauth2/{providerName}/callback")
    public ResponseEntity<String> getToken(AuthCodeReqDto authCodeReqDto
        , @PathVariable("providerName") String providerName
        , HttpServletResponse response
    ) throws JsonProcessingException {
        SocialType socialType = SocialType.GOOGLE.getSocial(providerName);
        String tokenInfo = oAuthUserService.login(authCodeReqDto, socialType);
        return ResponseEntity.ok(tokenInfo);
    }

    @PostMapping("logout/{provider}")
    public void userLogOut(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable(name = "provider") String provider,
                           @AuthenticationPrincipal UserToken userToken) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, authentication);
    }
}
