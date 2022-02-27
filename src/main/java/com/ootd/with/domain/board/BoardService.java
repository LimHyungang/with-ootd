package com.ootd.with.domain.board;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import com.ootd.with.web.board.CreateBoardForm;
import com.ootd.with.web.board.UpdateBoardForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    // 메인 페이지에서 게시판별 퀵메뉴 보이기 위한 List<Board> 조회
    List<Board> findAll();

    // Board 상세 조회
    Board findById(Long boardId);

    // Board 단건 등록
    Board save(CreateBoardForm form);


    /**
     * void 반환으로 수정
     */
    // Board 단건 수정
    Board update(Board board, UpdateBoardForm form);

    // Board 단건 삭제
    void delete(Board board);

    // List<Post> 페이징
    Page<Post> findPostsByBoardIdAndStatusType(Long boardId, StatusType type, Pageable pageable);

//    // Board 내부 검색
//    Page<Post> findPostsBySearch(/*검색 키워드 담기 위한 파라미터 필요*/);

}
