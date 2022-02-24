package com.ootd.with.domain.comment;

import com.ootd.with.domain.board.Board;
import com.ootd.with.domain.board.BoardRepository;
import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.member.MemberRepository;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CommentServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    CommentService commentService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    void before() {
        Member member = memberRepository.findByEmail("test@test.com").orElse(null);
        assertThat(member).isNotNull();

        Board board = Board.builder()
                            .name("OOTD")
                            .build();
        Board saveBoard = boardRepository.save(board);

        Post post = Post.builder()
                            .member(member)
                            .board(saveBoard)
                            .title("title")
                            .content("content")
                            .likeyCount(0)
                            .build();
        Post savePost = postRepository.save(post);

        Comment comment = Comment.builder()
                                    .post(savePost)
                                    .member(member)
                                    .content("comment content")
                                    .statusType(StatusType.NORMAL)
                                    .likeyCount(0)
                                    .build();
        Comment saveComment = commentRepository.save(comment);
        savePost.getCommentList().add(saveComment);
    }

    @Test
    @DisplayName("Comment 저장 테스트")
    public void saveComment() throws Exception {
        //given
        Member member = memberRepository.findByEmail("test@test.com").orElse(null);
        Post post = postRepository.findByTitle("title").orElse(null);
        assertThat(member).isNotNull();
        assertThat(post).isNotNull();

        String content = "컨텐트입니다.";
        Long commentId = commentService.saveComment(member, post.getId(), content);

        //when
        Comment findComment = commentRepository.findById(commentId).orElse(null);

        //then
        assertThat(findComment).isNotNull();
        assertThat(findComment.getContent()).isEqualTo(content);
        assertThat(findComment.getMember().getEmail()).isEqualTo(member.getEmail());
        assertThat(findComment.getPost().getTitle()).isEqualTo("title");
    }

    @Test
    @DisplayName("Comment 업데이트 테스트")
    public void updateComment() throws Exception {
        //given
        Comment comment = commentRepository.findByContent("comment content").orElse(null);
        assertThat(comment).isNotNull();

        //when
        String updateContent = "comment content 123";
        commentService.updateComment(comment.getId(), updateContent);

        System.out.println("======");
        em.flush();
        em.clear();
        System.out.println("======");

        //then
        assertThat(comment.getContent()).isEqualTo(updateContent);
    }

    @Test
    @DisplayName("Comment 삭제 테스트")
    public void deleteComment() throws Exception {
        //given
        Comment comment = commentRepository.findByContent("comment content").orElse(null);
        assertThat(comment).isNotNull();

        //when
        commentService.deleteComment(comment.getId());

        System.out.println("======");
        em.flush();
        em.clear();
        System.out.println("======");

        //then
        assertThat(comment.getStatusType()).isEqualTo(StatusType.DELETED);
    }

    @Test
    @DisplayName("로그인한 사용자가 작성자인지 판별 테스트")
    public void isLoginMemberAuthor() throws Exception {
        //given
        Member member = memberRepository.findByEmail("test@test.com").orElse(null);
        assertThat(member).isNotNull();

        //when
        Comment comment = commentRepository.findByContent("comment content").orElse(null);
        assertThat(comment).isNotNull();

        //then
        assertThat(commentService.isLoginMemberAuthor(member, comment.getId())).isTrue();
    }

    @Test
    @DisplayName("Comment 조회 테스트")
    public void findComment() throws Exception {
        //given
        Comment comment = commentRepository.findByContent("comment content").orElse(null);
        assertThat(comment).isNotNull();

        //when
        Member member = comment.getMember();
        String content = "comment content 123";
        Long saveCommentId = commentService.saveComment(member, comment.getPost().getId(), content);

        //then
        assertThat(commentService.findByCommentId(saveCommentId).getContent()).isEqualTo(content);
        assertThat(commentService.findAllByPostId(comment.getPost().getId()).size()).isEqualTo(2);
    }
    
}