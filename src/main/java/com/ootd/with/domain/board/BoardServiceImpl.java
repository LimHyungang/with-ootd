package com.ootd.with.domain.board;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import com.ootd.with.web.board.CreateBoardForm;
import com.ootd.with.web.board.UpdateBoardForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    /**
     * 1. 메인페이지의 Board 별 퀵메뉴를 위해 필요 (HomeController)
     * 모든 Board 를 긁어온 후 Board 별로 작게 페이징한 Page<Post>가 필요하다
     * -> findPostsByBoardId()
     *
     * 2. board 수정/삭제를 위한 board 정보 모아보기
     * only for admin
     */
    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    /**
     * Board 단건 조회
     * 보여지는 Board 에 대해서 요청이 들어오기 떄문에 null 가능성 사실상 없음
     * 이걸로 받은 Board 정보와 함께 Page<Post>도 추가로 필요하다
     * -> findPostsByBoardId()
     */
    @Override
    public Board findById(Long boardId) {
        return boardRepository.findById(boardId).orElse(null);
    }

    /**
     * Board 단건 등록
     * only for admin
     */
    @Override
    public Board save(CreateBoardForm form) {
        Board board = Board.createBoard(form);
        return boardRepository.save(board);
    }

    /**
     * Board 단건 수정
     * only for admin
     */
    @Override
    public Board update(Board board, UpdateBoardForm form) {
        return board.updateBoard(form);
    }

    /**
     * Board 단건 삭제
     * 상태값을 변경할 뿐 실제로 삭제 처리가 이뤄지진 않는다
     * only for admin
     */
    @Override
    public void delete(Board board) {
        board.changeStatus(StatusType.DELETED);
    }

    /**
     * Board 에 소속된 Post 페이징
     * 메인페이지의 퀵메뉴, Board 단건 조회 이후에 사용된다
     */
    @Override
    public Page<Post> findPostsByBoardIdAndStatusType(Long boardId, StatusType type, Pageable pageable) {
        Board board = boardRepository.findById(boardId).orElse(null);

        // null 받으면 해당 board 없음. 호출부에서 예외처리
        // 사실 확인 가능한 board 에 대해서만 호출이 일어나니까 null 나올 일이 없긴 함
        if(board == null) {
            return null;
        }

        return boardRepository.findPostsByBoardIdAndStatusType(boardId, StatusType.NORMAL, pageable);
    }

    /**
     * Board 내부 검색
     * 검색 조건을 받아 페이징하여 내보냄
     * 동적 쿼리 필요
     */
//    @Override
//    public Page<Post> findPostsBySearch() {
//        return null;
//    }
}
