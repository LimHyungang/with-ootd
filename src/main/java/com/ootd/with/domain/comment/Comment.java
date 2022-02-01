package com.ootd.with.domain.comment;

import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.likey.CommentLikey;
import com.ootd.with.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "comment_content")
    private String content;

    @Column(name = "comment_likey_count")
    private int likeyCount;

    @OneToMany(mappedBy = "comment")
    private List<CommentLikey> likeys = new ArrayList<>();
}
