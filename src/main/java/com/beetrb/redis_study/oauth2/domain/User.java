package com.beetrb.redis_study.oauth2.domain;

import com.beetrb.redis_study.oauth2.domain.provider.ProviderUserInfo;
import com.beetrb.redis_study.oauth2.domain.provider.SocialType;
import com.github.f4b6a3.tsid.Tsid;
import com.github.f4b6a3.tsid.TsidCreator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    private String id;
    private String nickname;
    private String displayName; // provider에서 제공하는 닉네임
    private String domesticId; // provider에서 제공하는 고유 회원 SEQ
    private SocialType socialType;
    private UserRole role;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime lastLoginedTime;

    public static User create(ProviderUserInfo providerUser) {
        return User.builder()
            .id(TsidCreator.getTsid4096().toString())
            .nickname(providerUser.getName())
            .displayName(providerUser.getName())
            .domesticId(providerUser.getDomesticId())
            .socialType(providerUser.getSocialType())
            .role(UserRole.MEMBER)
            .build();
    }

    public void setLastLoginedTime() {
        this.lastLoginedTime = LocalDateTime.now();
    }
}
