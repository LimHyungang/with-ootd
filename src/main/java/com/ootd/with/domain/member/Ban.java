package com.ootd.with.domain.member;

import com.ootd.with.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Entity
public class Ban extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "ban_id")
    private Long id;

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banned_member_id")
    private Member bannedMember;

    private Ban() {}

    public Ban(Member member, Member bannedMember) {
        this.member = member;
        this.bannedMember = bannedMember;
        member.getBanList().add(this);  // 연관관계 편의
    }
}
