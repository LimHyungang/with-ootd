package com.ootd.with.domain.post;

import com.ootd.with.domain.board.Board;
import com.ootd.with.domain.comment.Comment;
import com.ootd.with.domain.likey.PostLikey;
import com.ootd.with.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    private String title;

    @Lob
    @Column(name = "post_content")
    private String content;

    @Column(name = "post_likey_count")
    private int likeyCount;

    @OneToMany(mappedBy = "post")
    private List<PostLikey> likeys = new ArrayList<>();
}
