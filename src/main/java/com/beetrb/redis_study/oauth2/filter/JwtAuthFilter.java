package com.beetrb.redis_study.oauth2.filter;

import com.beetrb.redis_study.oauth2.jwt.AuthTokenConverter;
import com.beetrb.redis_study.oauth2.jwt.UserToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    @Value("${bbeudde.auth.header.scheme}")
    private String TOKEN_SCHEME;

    private final AuthTokenConverter authTokenConverter;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if(request.getMethod().equals(HttpMethod.OPTIONS)) {
            return true;
        }
        return super.shouldNotFilter(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenString = request.getHeader(this.TOKEN_SCHEME);

        if(StringUtils.hasText(tokenString)) {
            UserToken userToken = authTokenConverter.fromToken(tokenString);
            List<GrantedAuthority> authorityList =
                AuthorityUtils.createAuthorityList(userToken.getRole().name())
                ;
            UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userToken, (Object) null, authorityList);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
