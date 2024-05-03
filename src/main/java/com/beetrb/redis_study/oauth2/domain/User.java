package com.beetrb.redis_study.oauth2.domain;

import com.beetrb.redis_study.oauth2.domain.provider.SocialType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    private String id;
    private String nickname;
    private String displayName; // provider에서 제공하는 닉네임
    private String domesticId; // provider에서 제공하는 고유 회원 SEQ
    private SocialType socialType;
    private UserRole role;
    
}
