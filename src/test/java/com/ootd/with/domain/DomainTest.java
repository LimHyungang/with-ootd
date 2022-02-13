package com.ootd.with.domain;

import com.ootd.with.domain.board.Board;
import com.ootd.with.domain.board.BoardRepository;
import com.ootd.with.domain.comment.Comment;
import com.ootd.with.domain.comment.CommentRepository;
import com.ootd.with.domain.enumtype.RoleType;
import com.ootd.with.domain.enumtype.SexType;
import com.ootd.with.domain.hashtag.Hashtag;
import com.ootd.with.domain.hashtag.HashtagRepository;
import com.ootd.with.domain.post.PostHashtag;
import com.ootd.with.domain.post.PostHashtagRepository;
import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.member.MemberRepository;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DomainTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private HashtagRepository hashtagRepository;
    @Autowired
    private PostHashtagRepository postHashtagRepository;

    @BeforeEach
    void before() {
        Member member = Member.builder()
                .name("John Smith")
                .email("test@test.com")
                .password("asdf")
                .firstPhoneNumber("010")
                .midPhoneNumber("1234")
                .lastPhoneNumber("5678")
                .roleType(RoleType.ADMIN)
                .nickName("Tester")
                .sex(SexType.MALE)
                .build();
        memberRepository.save(member);

        Board board = Board.builder()
                .name("Daily Look")
                .build();
        boardRepository.save(board);

        Post post = Post.builder()
                .board(board)
                .member(member)
                .title("제목입니다")
                .content("본문입니다")
                .likeyCount(0)
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .content("댓글 내용입니다")
                .likeyCount(0)
                .build();
        commentRepository.save(comment);

        Hashtag hashtag = Hashtag.builder()
                .name("Nike")
                .build();
        hashtagRepository.save(hashtag);
    }

    @Test
    void postHashtagTest() {
        Post post = postRepository.findAll().get(0);
        Hashtag hashtag = hashtagRepository.findAll().get(0);

        PostHashtag postHashtag = PostHashtag.builder()
                .post(post)
                .hashtag(hashtag)
                .build();
        PostHashtag savePostHashtag = postHashtagRepository.save(postHashtag);

        assertThat(savePostHashtag.getPost().getId()).isEqualTo(post.getId());
        assertThat(savePostHashtag.getHashtag().getId()).isEqualTo(hashtag.getId());
    }
}