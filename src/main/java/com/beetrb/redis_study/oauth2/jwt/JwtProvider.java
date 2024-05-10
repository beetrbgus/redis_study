package com.beetrb.redis_study.oauth2.jwt;

import com.beetrb.redis_study.user.domain.UserCustomPrincipal;
import com.beetrb.redis_study.oauth2.exception.ApiException;
import com.beetrb.redis_study.oauth2.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${bbeudde.jwt.accessToken.expiryMills}")
    private long ACCESS_TOKEN_EXPIRE;

    @Value("${bbeudde.jwt.refreshToken.expiryMills}")
    private long REFRESH_TOKEN_EXPIRE;
    private final String tokenType = "Bearer ";
    @Value("${bbeudde.jwt.secret}")
    private String SECRETKEY;

    public String generateToken(UserCustomPrincipal userPrincipal) {
        final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRETKEY);
        final Key KEY = Keys.hmacShaKeyFor(apiKeySecretBytes);
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE);

        return Jwts.builder()
            .signWith(KEY, SignatureAlgorithm.HS512)
            .setSubject(userPrincipal.getNickName())
            .setClaims(createClaims(userPrincipal))
            .setIssuedAt(now)
            .setExpiration(expiry)
            .compact();
    }

    public TokenInfo generateTokenDto(UserCustomPrincipal userPrincipal) {
        final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRETKEY);
        final Key KEY = Keys.hmacShaKeyFor(apiKeySecretBytes);
        Date now = new Date();

        String accessToken = Jwts.builder()
            .signWith(KEY, SignatureAlgorithm.HS512)
            .setSubject((userPrincipal.getNickName()))
            .setClaims(createClaims(userPrincipal))
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE))
            .compact();

        String refreshToken = Jwts.builder()
            .signWith(KEY, SignatureAlgorithm.HS512)
            .setSubject((userPrincipal.getNickName()))
            .setClaims(createClaims(userPrincipal))
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE))
            .compact();

        return TokenInfo.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType(tokenType)
            .build();
    }

    public Map<String,Object> createClaims(UserCustomPrincipal userPrincipal) {
        Map<String,Object> claims = new HashMap<>();
        String roles = userPrincipal.getAuthorities()
            .stream().map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        claims.put("userId", userPrincipal.getUserId());
        claims.put("mbti", userPrincipal.getMbti());
        claims.put("nick", userPrincipal.getNickName());
        claims.put("role", roles);
        return claims;
    }

    public Claims getClaims(String token) throws ApiException {
        final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRETKEY);
        final Key KEY = Keys.hmacShaKeyFor(apiKeySecretBytes);
        try {
            return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (SecurityException e) {
            throw new ApiException(ErrorCode.INVALID_JWT_SIGNATURE);
        } catch (MalformedJwtException e) {
            // 유효하지 않은 구성의 토큰
            throw new ApiException(ErrorCode.MALFORMED_JWT_EXCEPTION);
        } catch (ExpiredJwtException e) {
            throw new ApiException(ErrorCode.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new ApiException(ErrorCode.UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            // 잘못된 JWT
            throw new ApiException(ErrorCode.INVALID_JWT_TOKEN);
        } catch (SignatureException e) {
            throw new ApiException(ErrorCode.INVALID_JWT_SIGNATURE);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ErrorCode.SYSTEM_EXCEPTION);
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = this.getClaims(token);
        if(claims == null || claims.isEmpty()) {
            throw new ApiException(ErrorCode.MALFORMED_JWT_EXCEPTION);
        }
        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get("role").toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        UserDetails principal = new User(claims.getSubject(),"", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);

    }
    public Long getUserId(String token) {
        return getClaims(token)
            .get("userId", Long.class);
    }
}
