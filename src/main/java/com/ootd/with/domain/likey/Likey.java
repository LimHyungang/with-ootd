package com.ootd.with.domain.likey;

import com.ootd.with.domain.member.Member;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Likey {

    @Id @GeneratedValue
    @Column(name = "likey_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
