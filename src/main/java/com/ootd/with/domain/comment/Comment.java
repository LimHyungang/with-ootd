package com.ootd.with.domain.comment;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLikey> likeyList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @Builder
    public Comment(Member member, Post post, String content, int likeyCount) {
        this.member = member;
        this.post = post;
        this.content = content;
        this.likeyCount = likeyCount;
    }
}
