package com.ootd.with.domain.member;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByMemberIdAndPostId(Long memberId, Long postId);

    @Query("select p from Bookmark b join b.post p where b.member.id = :memberId and p.statusType = :statusType")
    Page<Post> findPostsByMemberId(@Param("memberId") Long memberId, @Param("statusType") StatusType statusType, Pageable pageable);
}
