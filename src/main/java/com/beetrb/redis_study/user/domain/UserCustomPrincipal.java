package com.beetrb.redis_study.user.domain;

import com.beetrb.redis_study.user.domain.provider.SocialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class UserCustomPrincipal implements OAuth2User, UserDetails {
    private final String userId;
    private final String mbti;
    private final String nickName;
    private final UserRole roleType;
    private final SocialType socialType;
    private final UserStatus status;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserStatus.DELETED.equals(status);
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserStatus.BLOCK.equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.NORMAL.equals(status);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return nickName;
    }

    public static UserCustomPrincipal create(User user) {
        return UserCustomPrincipal.builder()
            .userId(user.getId())
            .nickName(user.getNickname())
            .roleType(user.getRole())
            .status(UserStatus.NORMAL)
            .socialType(user.getSocialType())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())))
            .build();
    }
}
