package com.ootd.with.domain.post;

import com.ootd.with.domain.board.Board;
import com.ootd.with.domain.board.BoardRepository;
import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.member.MemberRepository;
import com.ootd.with.domain.member.PhoneNumber;
import com.ootd.with.web.post.CreatePostForm;
import com.ootd.with.web.post.UpdatePostForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceImplTest {

    @Autowired MemberRepository memberRepository;
    @Autowired BoardRepository boardRepository;

    @Autowired PostService postService;
    @Autowired PostRepository postRepository;

    @Autowired EntityManager em;

    @Test
    public void saveTest() {
        // given
        List<Member> members = memberRepository.findAll();
        List<Board> boards = boardRepository.findAll();

        CreatePostForm form = new CreatePostForm();
        form.setMember(members.get(0));
        form.setBoard(boards.get(0));
        form.setTitle("title2");
        form.setContent("content2");
        form.setLikeyCount(0);
        form.setHashtagNames("hashtag1 hashtag2 hashtag3 hashtag4 hashtag5");

        // when
        Post save = postService.save(form);

        // then
        Post find = postRepository.findById(save.getId()).orElse(null);
        assertThat(find.getId()).isEqualTo(save.getId());
        assertThat(find.getPostHashtagList().size()).isEqualTo(5);
    }

    @Test
    public void findByIdAndStatusType() {
        // given
        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0);

        // when
        Post find = postService.findByIdAndStatusType(post.getId(), StatusType.NORMAL);

        // then
        assertThat(find.getId()).isEqualTo(post.getId());
    }

    @Test
    public void updateTest() {
        // given
        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0);

        // when
        UpdatePostForm updateForm = new UpdatePostForm();
        updateForm.setTitle("updated title");
        updateForm.setContent("updated content");
        postService.update(post, updateForm);

        // then
        Post find = postRepository.findById(post.getId()).orElse(null);
        assertThat(find.getTitle()).isEqualTo(updateForm.getTitle());
        assertThat(find.getContent()).isEqualTo(updateForm.getContent());
    }

    @Test
    public void deleteTest() {
        // given
        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0);

        // when
        postService.delete(post);
        em.flush();
        em.clear();

        // then
        Post find = postService.findByIdAndStatusType(post.getId(), StatusType.NORMAL);
        assertThat(find).isNull();
    }

    @BeforeEach
    public void init() {
        Member member1 = memberRepository.save(new Member("임현강", "asdf", "hyungang7@gmail.com",
                new PhoneNumber("010", "6878", "1422"),
                "MALE", "헤으응"));
        Board board1 = boardRepository.save(new Board("board1", StatusType.NORMAL));

        CreatePostForm form = new CreatePostForm();
        form.setMember(member1);
        form.setBoard(board1);
        form.setTitle("title1");
        form.setContent("content1");
        form.setLikeyCount(0);
        form.setHashtagNames("hashtag1 hashtag2 hashtag3 hashtag4 hashtag5");
        Post save = postService.save(form);

        em.flush();
        em.clear();
    }

}