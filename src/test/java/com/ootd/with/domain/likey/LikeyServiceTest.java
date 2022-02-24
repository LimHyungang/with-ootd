package com.ootd.with.domain.likey;

import com.ootd.with.domain.board.Board;
import com.ootd.with.domain.board.BoardRepository;
import com.ootd.with.domain.comment.Comment;
import com.ootd.with.domain.comment.CommentLikey;
import com.ootd.with.domain.comment.CommentRepository;
import com.ootd.with.domain.comment.CommentService;
import com.ootd.with.domain.enumtype.SexType;
import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.member.MemberRepository;
import com.ootd.with.domain.member.MemberService;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostLikey;
import com.ootd.with.domain.post.PostRepository;
import com.ootd.with.web.member.AddMemberForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class LikeyServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    LikeyService likeyService;

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

    @Autowired
    MemberService memberService;

    @BeforeEach
    void setUp() {
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

        likeyService.postLikey(member, post.getId());
        likeyService.commentLikey(member, comment.getId());
    }

    @Test
    @DisplayName("Post 좋아요 테스트")
    public void savePostLikey() throws Exception {
        //given
        AddMemberForm form = new AddMemberForm();
        form.setName("테스터2");
        form.setEmail("test2@test.com");
        form.setPassword("test!");
        form.setFirstPhoneNumber("010");
        form.setMidPhoneNumber("1234");
        form.setLastPhoneNumber("5678");
        form.setNickName("Tester2");
        form.setSexType(SexType.FEMALE.name());

        Member member = memberService.save(form);
        Post post = postRepository.findByTitle("title").orElse(null);
        assertThat(member).isNotNull();
        assertThat(post).isNotNull();

        //when
        likeyService.postLikey(member, post.getId());

        //then
        PostLikey findPostLikey = likeyService.findPostLikeyByMemberAndPostId(member, post.getId());
        assertThat(findPostLikey).isNotNull();
        assertThat(findPostLikey.getPost().getLikeyCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("Post 좋아요 취소 테스트")
    public void deletePostLikey() throws Exception {
        //given
        Member member = memberRepository.findByEmail("test@test.com").orElse(null);
        Post post = postRepository.findByTitle("title").orElse(null);
        assertThat(member).isNotNull();
        assertThat(post).isNotNull();

        //when
        likeyService.postLikey(member, post.getId());

        //then
        PostLikey findPostLikey = likeyService.findPostLikeyByMemberAndPostId(member, post.getId());
        assertThat(findPostLikey).isNull();

        Post findPost = postRepository.findByTitle("title").orElse(null);
        assertThat(findPost).isNotNull();
        assertThat(findPost.getLikeyCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("Comment 좋아요 테스트")
    public void saveCommentLikey() throws Exception {
        //given
        AddMemberForm form = new AddMemberForm();
        form.setName("테스터2");
        form.setEmail("test2@test.com");
        form.setPassword("test!");
        form.setFirstPhoneNumber("010");
        form.setMidPhoneNumber("1234");
        form.setLastPhoneNumber("5678");
        form.setNickName("Tester2");
        form.setSexType(SexType.FEMALE.name());

        Member member = memberService.save(form);
        Comment comment = commentRepository.findByContent("comment content").orElse(null);
        assertThat(member).isNotNull();
        assertThat(comment).isNotNull();

        //when
        likeyService.commentLikey(member, comment.getId());

        //then
        CommentLikey commentLikey = likeyService.findCommentLikeyByMemberAndPostId(member, comment.getId());
        assertThat(commentLikey).isNotNull();
        assertThat(commentLikey.getComment().getLikeyCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("Comment 좋아요 취소 테스트")
    public void deleteCommentLikey() throws Exception {
        //given
        Member member = memberRepository.findByEmail("test@test.com").orElse(null);
        Comment comment = commentRepository.findByContent("comment content").orElse(null);
        assertThat(member).isNotNull();
        assertThat(comment).isNotNull();

        //when
        likeyService.commentLikey(member, comment.getId());

        //then
        CommentLikey commentLikey = likeyService.findCommentLikeyByMemberAndPostId(member, comment.getId());
        assertThat(commentLikey).isNull();

        Comment findComment = commentRepository.findByContent("comment content").orElse(null);
        assertThat(findComment).isNotNull();
        assertThat(findComment.getLikeyCount()).isEqualTo(0);
    }

}