package com.ootd.with.domain.board;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.member.MemberRepository;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostRepository;
import com.ootd.with.web.board.CreateBoardForm;
import com.ootd.with.web.board.UpdateBoardForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class BoardServiceImplTest {

    @Autowired EntityManager em;
    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;

    @Test
    @Rollback
    public void findAllTest() {
        List<Board> all = boardService.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @Rollback
    public void findByIdTest() {
        List<Board> all = boardService.findAll();
        Board board1 = all.get(0);
        Board board2 = all.get(1);
        Board findBoard1 = boardService.findById(board1.getId());
        Board findBoard2 = boardService.findById(board2.getId());

        assertThat(findBoard1.getId()).isEqualTo(board1.getId());
        assertThat(findBoard2.getId()).isEqualTo(board2.getId());
    }

    @Test
    @Rollback
    public void saveTest() {
        CreateBoardForm form = new CreateBoardForm("board3", StatusType.NORMAL);
        Board save = boardService.save(form);
        Board findBoard = boardService.findById(save.getId());

        assertThat(findBoard.getId()).isEqualTo(save.getId());
    }

    @Test
    @Rollback
    public void updateTest() {
        List<Board> all = boardService.findAll();
        Board board1 = all.get(0);
        String oldName = board1.getName();
        StatusType oldType = board1.getStatusType();
        UpdateBoardForm form = new UpdateBoardForm("new name", StatusType.HIDDEN);

        boardService.update(board1, form);
        Board updatedBoard = boardService.findById(board1.getId());

        // is equal to form
        assertThat(updatedBoard.getName()).isEqualTo(form.getName());
        assertThat(updatedBoard.getStatusType()).isEqualTo(form.getStatusType());
        // is not equal to old board
        assertThat(updatedBoard.getName()).isNotEqualTo(oldName);
        assertThat(updatedBoard.getStatusType()).isNotEqualTo(oldType);
    }

    @Test
    @Rollback
    public void deleteTest() {
        List<Board> all = boardService.findAll();
        Board board1 = all.get(0);

        boardService.delete(board1);

        assertThat(board1.getStatusType()).isEqualTo(StatusType.DELETED);
    }

    @Test
    @Rollback
    public void findPostsByBoardIdAndStatusTypeTest() {
        List<Board> all = boardService.findAll();
        Board board1 = all.get(0);

        int pageNum = 0;

        PageRequest pageRequest = PageRequest.of(pageNum++, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Post> page1 = boardService.findPostsByBoardIdAndStatusType(board1.getId(), StatusType.NORMAL, pageRequest);

        pageRequest = PageRequest.of(pageNum++, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Post> page2 = boardService.findPostsByBoardIdAndStatusType(board1.getId(), StatusType.NORMAL, pageRequest);

        pageRequest = PageRequest.of(pageNum++, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Post> page3 = boardService.findPostsByBoardIdAndStatusType(board1.getId(), StatusType.NORMAL, pageRequest);

        pageRequest = PageRequest.of(pageNum++, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Post> page4 = boardService.findPostsByBoardIdAndStatusType(board1.getId(), StatusType.NORMAL, pageRequest);

        List<Post> content = page1.getContent();
        for (Post post : content) {
            System.out.println(post);
        }

        // page size
        assertThat(page1.getContent().size()).isEqualTo(3);
        assertThat(page2.getContent().size()).isEqualTo(3);
        assertThat(page3.getContent().size()).isEqualTo(3);
        assertThat(page4.getContent().size()).isEqualTo(1);

        // total count
        assertThat(page1.getTotalElements()).isEqualTo(10);
        assertThat(page2.getTotalElements()).isEqualTo(10);
        assertThat(page3.getTotalElements()).isEqualTo(10);
        assertThat(page4.getTotalElements()).isEqualTo(10);

        // total page
        assertThat(page1.getTotalPages()).isEqualTo(4);
        assertThat(page2.getTotalPages()).isEqualTo(4);
        assertThat(page3.getTotalPages()).isEqualTo(4);
        assertThat(page4.getTotalPages()).isEqualTo(4);

        // page num
        assertThat(page1.getNumber()).isEqualTo(0);
        assertThat(page2.getNumber()).isEqualTo(1);
        assertThat(page3.getNumber()).isEqualTo(2);
        assertThat(page4.getNumber()).isEqualTo(3);
    }

//    @Test
//    public void findPostsBySearch() {
//
//    }

    @BeforeEach
    public void init() {
        Member member1 = memberRepository.save(new Member("임현강", "asdf", "hyungang7@gmail.com",
                "010", "6878", "1422",
                "MALE", "헤으응"));
        Member member2 = memberRepository.save(new Member("정우용", "asdf", "wooyong7@gmail.com",
                "010", "1234", "5678",
                "MALE", "헤우웅"));
        Board board1 = boardRepository.save(new Board("board1", StatusType.NORMAL));
        Board board2 = boardRepository.save(new Board("board2", StatusType.NORMAL));

//        for(int i = 0; i < 10; i++) {
//            Post post = new Post(member1, board1, "board1 title" + i, "board1 content" + i, 0);
//            postRepository.save(post);
//        }
//        for(int i = 0; i < 10; i++) {
//            Post post = new Post(member2, board2, "board2 title" + i, "board2 content" + i, 0);
//            postRepository.save(post);
//        }

        em.flush();
        em.clear();
    }
}