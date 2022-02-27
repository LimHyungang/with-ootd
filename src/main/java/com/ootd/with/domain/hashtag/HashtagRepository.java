package com.ootd.with.domain.hashtag;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Optional<Hashtag> findByName(String hashtagName);

    @Query("select h from PostHashtag ph join ph.hashtag h on ph.post.id = :postId")
    List<Hashtag> findAllByPostId(@Param("postId") Long postId);  // 이것도 JPQL 빠져도 알아서 작성해주려나? -> 안 해준다. 기본적으로 Hashtag 테이블을 기준으로만 조회함

    // 조인 쿼리를 매번 보내야 하지만 페이징 기능을 사용하기 위해 어쩔 수 없음
    @Query("select p from PostHashtag ph join ph.post p on ph.hashtag.name = :name and p.statusType = :type")
    Page<Post> findPostsByHashtagNameAndStatusType(@Param("name") String hashtagName, @Param("type") StatusType type, Pageable pageable);
}
