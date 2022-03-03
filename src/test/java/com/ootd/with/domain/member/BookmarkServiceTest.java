package com.ootd.with.domain.member;

import com.ootd.with.domain.board.Board;
import com.ootd.with.domain.board.BoardRepository;
import com.ootd.with.domain.enumtype.SexType;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostRepository;
import com.ootd.with.web.member.CreateMemberForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

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

        bookmarkService.bookmark(member.getId(), post.getId());
    }

    @Test
    @DisplayName("북마크 등록 테스트")
    public void bookmark() throws Exception {
        //given
        CreateMemberForm form = new CreateMemberForm();
        form.setName("테스터2");
        form.setEmail("test2@test.com");
        form.setPassword("test!");
        form.setPhoneNumber(new PhoneNumber("010", "1234", "5678"));
        form.setNickName("Tester2");
        form.setSexType(SexType.FEMALE.name());

        Member member = memberService.save(form);
        Post post = postRepository.findByTitle("title").orElse(null);
        assertThat(member).isNotNull();
        assertThat(post).isNotNull();

        //when
        bookmarkService.bookmark(member.getId(), post.getId());
        Bookmark findBookmark = bookmarkService.findOne(member.getId(), post.getId());

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
        bookmarkService.bookmark(member.getId(), post.getId());

        //then
        assertThat(bookmarkService.findOne(member.getId(), post.getId())).isNull();
    }


    @Test
    @DisplayName("멤버가 북마크한 Post 리스트 가져오기 테스트")
    void query() {
        Member member = memberRepository.findByEmail("test@test.com").orElse(null);
        assertThat(member).isNotNull();

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Post> posts = bookmarkService.findPostsByMemberId(member.getId(), pageRequest);

        assertThat(posts.getTotalElements()).isEqualTo(1);
        assertThat(posts.getContent().get(0).getTitle()).isEqualTo("title");
    }
}