package com.ootd.with.domain.member;

import com.ootd.with.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByMemberAndPostId(Member member, Long postId);
    List<Bookmark> findAllByMember(Member member);

    @Query("select p from Bookmark b join b.post p on b.post.board.id = :boardId and b.member = :member")
    List<Post> findPostsByMemberAndBoardId(@Param("member") Member member, @Param("boardId") Long boardId);
}
