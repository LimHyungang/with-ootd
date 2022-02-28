package com.ootd.with.domain.member;

import com.ootd.with.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ban extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "ban_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banned_member_id")
    private Member bannedMember;

    @Builder
    public Ban(Member member, Member bannedMember) {
        this.member = member;
        this.bannedMember = bannedMember;
        member.getBanList().add(this);  // 연관관계 편의
    }

}
