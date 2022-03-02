package com.ootd.with.domain.post;

import com.ootd.with.domain.BaseTimeEntity;
import com.ootd.with.domain.board.Board;
import com.ootd.with.domain.comment.Comment;
import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.member.Member;
import com.ootd.with.web.post.CreatePostForm;
import com.ootd.with.web.post.UpdatePostForm;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @NotBlank
    private String title;

    @NotBlank
    @Lob
    @Column(name = "post_content")
    private String content;

    @NotNull
    @Column(name = "post_likey_count")
    private Integer likeyCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLikey> likeyList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostHashtag> postHashtagList = new ArrayList<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @Builder
    public Post(Member member, Board board, String title, String content, Integer likeyCount, StatusType statusType) {
        this.member = member;
        this.board = board;
        this.title = title;
        this.content = content;
        this.likeyCount = likeyCount;
        this.statusType = statusType;
    }


    public void changeStatus(StatusType type) {
        this.statusType = type;
    }

    public static Post createPost(CreatePostForm form) {
        // Post 생성
        Post post = Post.builder()
                .member(form.getMember())
                .board(form.getBoard())
                .title(form.getTitle())
                .content(form.getContent())
                .likeyCount(form.getLikeyCount())
                .statusType(StatusType.NORMAL)
                .build();

        // 연관관계 편의
        form.getMember().getPostList().add(post);
        form.getBoard().getPostList().add(post);

        return post;
    }

    public void updatePost(UpdatePostForm form) {
        if(form.getTitle() != null) this.title = form.getTitle();
        if(form.getContent() != null) this.content = form.getContent();
    }
}
