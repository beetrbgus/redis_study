package com.beetrb.redis_study.oauth2.jwt;

import com.beetrb.redis_study.oauth2.domain.UserCustomPrincipal;
import com.beetrb.redis_study.oauth2.domain.UserRole;
import com.beetrb.redis_study.oauth2.exception.ApiException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenConverter {
    private final JwtProvider jwt;

    public UserToken fromToken(String t) throws ApiException {
        Claims claims = jwt.getClaims(t);
        String userId = claims.get("userId", String.class);
        String displayName = claims.get("nick", String.class);
        String role = claims.get("role", String.class);
        return new UserToken(userId, displayName, UserRole.matchKey(role));
    }

    public String principalToToken(UserCustomPrincipal principal) {
        return jwt.generateToken(principal);
    }

    public TokenInfo principalToTokenInfo(UserCustomPrincipal principal) {
        return jwt.generateTokenDto(principal);
    }
    public void validate(String token) throws ApiException {
        jwt.getClaims(token);
    }
}