package com.ootd.with.web.board;

import com.ootd.with.domain.board.Board;
import com.ootd.with.domain.board.BoardService;
import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.post.Post;
import com.ootd.with.web.argumentresolver.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    /**
     *
     * 상태 체크 필요 (NORMAL 상태만 조회하기)
     *
     *
//     * - 1.1 메인페이지에서 board 별 퀵메뉴 보여주기
//     *      *     - NORMAL Status 한정 조회
     *
     *
     * 1. board 수정/삭제 페이지 입장 전 board 들 모아서 보여주기
     *   - 여기선 Status 관계 없이 모든 board 보여줘야 함
     * 2. board 단건 조회
     *   - 이후 post page 조회 필요
     * 3. board 단건 등록
     *   - 1 에서 등록 버튼 클릭 시 등록 페이지 이동
     *   - 등록
     * 4. board 단건 수정
     *   - 1 에서 수정 버튼 클릭 시 수정 페이지 이동
     *   - 수정
     * 5. board 단건 삭제
     *   - 1 에서 삭제 버튼 클릭 시 바로 삭제 요청
     * 6. board 검색 ( 동적 쿼리 통한 post page 조회 )
     *   - 검색
     */


    /**
     * List<Board>조회
     * board 등록/수정/삭제를 위한 board 정보 모아보기
     */
    @GetMapping("/boards")
    public String readBoardList(@Admin Member admin, Model model) {
        if(admin == null) {
            return "redirect:/";
        }

        List<Board> boardList = boardService.findAll();
        model.addAttribute("boards", boardList);
        return "";  // board list 조회 페이지
    }

    /**
     * Board 내부 입장
     */
    @GetMapping("/board/{id}/{page}")
    public String readBoard(@PathVariable("id") Long boardId,
                            @PathVariable("page") int pageNum,
                            Pageable pageable, Model model) {
        Board board = boardService.findById(boardId);
        if(board == null) {
            // 예외처리
        }

        Page<Post> page = boardService.findPostsByBoardIdAndStatusType(board.getId(), StatusType.NORMAL, pageable);  // 일단은 한 페이지 당 10으로
        List<Post> content = page.getContent();
        model.addAttribute("board", board);
        model.addAttribute("posts", content);

        return "";  // Board 내부 페이지
    }

    /**
     * 등록 페이지 이동
     */
    @GetMapping("/board/new")
    public String createBoardForm(@Admin Member admin,
                                  @ModelAttribute("board") CreateBoardForm form) {
        if(admin == null) {
            return "/";
        }
        return "";  // 등록 페이지
    }

    /**
     * 등록 요청
     */
    @PostMapping("/board/new")
    public String createBoard(@Admin Member admin,
                              @Valid @ModelAttribute("board") CreateBoardForm form, BindingResult bindingResult,
                              Model model) {
        if(admin == null) {
            return "/";
        }
        if (bindingResult.hasErrors()) {
            return "";  // Board 등록 페이지
        }
        Board board = boardService.save(form);
        model.addAttribute("board", board);
        return "/";  // board list 조회 페이지
    }

    /**
     * 수정 페이지 이동
     */
    @GetMapping("/board/{id}/edit")
    public String updateBoardForm(@Admin Member admin,
                                  @ModelAttribute("board") UpdateBoardForm form) {
        if(admin == null) {
            return "/";
        }
        return "";  // Board 수정 페이지
    }

    /**
     * 수정 요청
     */
    @PatchMapping("/board/{id}/edit")
    public String updateBoard(@Admin Member admin,
                              @Valid @ModelAttribute("board") UpdateBoardForm form, BindingResult bindingResult,
                              @PathVariable("id") Long boardId,
                              Model model) {
        if(admin == null) {
            return "/";
        }
        if (bindingResult.hasErrors()) {
            return "";  // Board 수정 페이지
        }
        Board board = boardService.findById(boardId);
        if(board == null) {
            // 예외처리
        }
        boardService.update(board, form);
        return "";  // redirect board list 조회 페이지
    }

    /**
     * 삭제 요청
     */
    @DeleteMapping("/board/{id}")
    public String deleteBoard(@Admin Member admin,
                              @PathVariable("id") Long boardId) {
        if(admin == null) {
            return "/";
        }

        Board board = boardService.findById(boardId);
        if (board == null) {
            // 예외처리
        }
        boardService.delete(board);
        return "";  // Response 객체 만들어서 반환
    }

    /**
     * 검색
     */
    @GetMapping("/board/search")
    public String searchPostList() {

        /**
         * Search Form 을 어떻게 받을 것인가?
         * 일단 기준.. title, content, author,
         */

        return "";  // Board 내부 페이지
    }
}
