package com.ootd.with.domain.member;

import com.ootd.with.domain.board.Board;
import com.ootd.with.domain.board.BoardRepository;
import com.ootd.with.domain.enumtype.SexType;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostRepository;
import com.ootd.with.web.member.AddMemberForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class BookmarkServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    BookmarkService bookmarkService;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    PostRepository postRepository;

    Board board;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.findByEmail("test@test.com").orElse(null);
        assertThat(member).isNotNull();

        board = Board.builder()
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

        bookmarkService.bookmark(member, post.getId());
    }

    @Test
    @DisplayName("북마크 등록 테스트")
    public void bookmark() throws Exception {
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
        bookmarkService.bookmark(member, post.getId());
        Bookmark findBookmark = bookmarkService.findOne(member, post.getId());

        //then
        assertThat(findBookmark).isNotNull();
        assertThat(findBookmark.getMember().getEmail()).isEqualTo("test2@test.com");
        assertThat(findBookmark.getPost().getTitle()).isEqualTo("title");
    }

    @Test
    @DisplayName("북마크 해제 테스트")
    public void unBookmark() throws Exception {
        //given
        Member member = memberRepository.findByEmail("test@test.com").orElse(null);
        Post post = postRepository.findByTitle("title").orElse(null);
        assertThat(member).isNotNull();
        assertThat(post).isNotNull();

        //when
        bookmarkService.bookmark(member, post.getId());

        //then
        assertThat(bookmarkService.findOne(member, post.getId())).isNull();
        assertThat(bookmarkService.findAllByMember(member).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("게시판 Bookmark 글 찾기 테스트")
    public void findBookmarkByBoard() throws Exception {
        //given
        Member member = memberRepository.findByEmail("test@test.com").orElse(null);
        assertThat(member).isNotNull();

        //when
        List<Post> posts = bookmarkService.findPostsByMemberAndBoardId(member, board.getId());

        //then
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getBoard().getName()).isEqualTo("OOTD");
    }



}