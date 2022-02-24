package com.ootd.with.domain.post;

import com.ootd.with.domain.BaseTimeEntity;
import com.ootd.with.domain.board.Board;
import com.ootd.with.domain.comment.Comment;
import com.ootd.with.domain.enumtype.StatusType;
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
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    private String title;

    @Lob
    @Column(name = "post_content")
    private String content;

    @Column(name = "post_likey_count")
    private int likeyCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLikey> likeyList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostHashtag> hashtagList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @Builder
    public Post(Member member, Board board, String title, String content, int likeyCount) {
        this.member = member;
        this.board = board;
        this.title = title;
        this.content = content;
        this.likeyCount = likeyCount;
    }

    //==연관관계 메서드==//
    public void addLikeyCount() {
        // 변경 감지 이용
        this.likeyCount++;
    }

    public void subtractLikeyCount() {
        // 변경 감징 이용
        this.likeyCount--;
    }

}
