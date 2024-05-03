package com.beetrb.redis_study.oauth2.filter;

import com.beetrb.redis_study.common.CommonResponse;
import com.beetrb.redis_study.oauth2.exception.ApiException;
import com.beetrb.redis_study.oauth2.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ApiException e) {
            setErrorResponse(request, response, e);
        }
    }

    private void setErrorResponse(HttpServletRequest request, HttpServletResponse response, ApiException e) throws IOException {
        ErrorCode errorCode = e.getErrorCode();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorCode.getStatus());

        CommonResponse failureRes = CommonResponse.fail(errorCode);
        objectMapper.writeValue(response.getOutputStream(), failureRes);
    }
}
