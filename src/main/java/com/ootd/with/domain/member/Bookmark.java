package com.ootd.with.domain.member;

import com.ootd.with.domain.BaseTimeEntity;
import com.ootd.with.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Bookmark extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Bookmark(Member member, Post post) {
        this.member = member;
        this.post = post;
    }
}
