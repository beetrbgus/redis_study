package com.beetrb.redis_study.oauth2.config;

import com.beetrb.redis_study.oauth2.filter.JwtAuthFilter;
import com.beetrb.redis_study.oauth2.filter.JwtExceptionFilter;
import com.beetrb.redis_study.oauth2.handler.jwt.JwtAccessDeniedHandler;
import com.beetrb.redis_study.oauth2.handler.jwt.JwtAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final ObjectMapper objectMapper;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(corsConfiguration -> corsConfiguration.disable())
            .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers("/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/notice").hasAnyRole()
            .antMatchers(HttpMethod.GET,"/api/notice").permitAll()
        ;
        http.formLogin(formLogin -> formLogin.disable())
            .csrf(csrf-> csrf.disable())
            .exceptionHandling(
                exceptionHandling ->
                    exceptionHandling.accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
        ;
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtExceptionFilter(objectMapper), JwtAuthFilter.class)
            .sessionManagement(sessionManageMent-> sessionManageMent.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;
        return http.build();
    }
}
