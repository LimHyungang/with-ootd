package com.ootd.with.domain.board;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {


    // Board 내부 Post 페이징
    // Board 는 post list 가지고 있으니 따로 JPQL 필요 없나? -> ㄴㄴ 필요..
    @Query("select p from Post p where p.board.id = :id and p.statusType = :type" )
    Page<Post> findPostsByBoardIdAndStatusType(@Param("id") Long boardId, @Param("type") StatusType type, Pageable pageable);

    // Board
//    Page<Post> findPostsBySearch();
}
