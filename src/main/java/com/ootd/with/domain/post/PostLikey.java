package com.ootd.with.domain.post;

import com.ootd.with.domain.likey.Likey;
import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostLikey extends Likey {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostLikey(Member member, Post post) {
        super(member);
        this.post = post;
    }
}

