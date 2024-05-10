package com.beetrb.redis_study.oauth2.jwt;

import com.beetrb.redis_study.oauth2.converter.UserRoleConverter;
import com.beetrb.redis_study.user.domain.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserToken {
    private String userId;
    private String nick;
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;
}
