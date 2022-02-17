package com.ootd.with.domain.likey;

import com.ootd.with.domain.BaseTimeEntity;
import com.ootd.with.domain.member.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Likey extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "likey_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Likey(Member member) {
        this.member = member;
    }
}
